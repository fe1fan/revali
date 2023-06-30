package io.xka.revali.data.mysql;

import io.xka.revali.core.datasource.RevaliDatasourceConnection;
import io.xka.revali.core.datasource.RevaliDatasourceConnectionConfiguration;
import io.xka.revali.core.datasource.RevaliDatasourceStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

public class MySQLConn implements RevaliDatasourceConnection {

    private Connection connection;

    private RevaliDatasourceStatus status;


    @Override
    public RevaliDatasourceConnection connected(RevaliDatasourceConnectionConfiguration configuration) {
        try {
            connection = DriverManager.getConnection(
                    configuration.getUrl(),
                    configuration.getUsername(),
                    configuration.getPassword());
        } catch (SQLException exception) {
            status = RevaliDatasourceStatus.FAILED;
            throw new RuntimeException(exception);
        }
        status = RevaliDatasourceStatus.CONNECTED;
        return this;
    }

    @Override
    public boolean isAlive() {
        return RevaliDatasourceStatus.CONNECTED == status;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        status = RevaliDatasourceStatus.DISCONNECTED;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void submit(String sql, Consumer<T> callback) {
        //执行SQL
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet resultSet = statement.executeQuery(sql);
            //如果返回值是List
            if (List.class.isAssignableFrom(callback.getClass())) {
                List<T> list = (List<T>) callback;
                while (resultSet.next()) {
                    //获取所有的列
                    int columnCount = resultSet.getMetaData().getColumnCount();
                    //获取所有的列的值
                    Object[] values = new Object[columnCount];
                    //获取所有列的名字
                    String[] columnNames = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        values[i] = resultSet.getObject(i + 1);
                        columnNames[i] = resultSet.getMetaData().getColumnName(i + 1);
                    }
                    //通过反射创建对象
                    T t = (T) Class.forName(resultSet.getMetaData().getTableName(1)).newInstance();
                    //通过反射设置对象的值
                    for (int i = 0; i < columnCount; i++) {
                        t.getClass().getDeclaredField(columnNames[i]).set(t, values[i]);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public <T> T execute(String sql) {
        return null;
    }
}
