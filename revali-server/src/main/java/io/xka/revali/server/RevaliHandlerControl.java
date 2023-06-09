package io.xka.revali.server;

import io.xka.revali.core.serialization.SerializationAdopter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RevaliHandlerControl {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final SerializationAdopter serializationAdopter;

    public RevaliHandlerControl(HttpServletRequest request, HttpServletResponse response, SerializationAdopter serializationAdopter) {
        this.request = request;
        this.response = response;
        this.serializationAdopter = serializationAdopter;
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    // =============================================== http request methods ===============================================

    /**
     * get query parameter
     *
     * @param key query key
     * @return query value
     */
    public String getQuery(String key) {
        return request.getParameter(key);
    }

    /**
     * get query parameter
     *
     * @param clazz query class
     * @return query object
     */
    public <T> T getQuery(Class<? extends T> clazz) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> convertMap = new HashMap<>(parameterMap.size());
        parameterMap.forEach(
                (key, value) -> {
                    if (value.length < 2) {
                        convertMap.put(key, value[0]);
                    }
                    if (value.length > 1) {
                        convertMap.put(key, Arrays.stream(value).collect(Collectors.toList()));
                    }
                }
        );
        String body = serializationAdopter.toJson(convertMap);
        return serializationAdopter.toObj(body, clazz);
    }

    /**
     * get header value
     *
     * @param key header key
     * @return header value
     */
    public String getHeader(String key) {
        return request.getHeader(key);
    }

    /**
     * get cookie value
     *
     * @param key cookie key
     * @return cookie value
     */
    public String getCookie(String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(key)).findFirst();
        return cookie.map(Cookie::getValue).orElse(null);
    }

    /**
     * get all cookies
     *
     * @return all cookies
     */
    public String[] getCookies() {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies).map(Cookie::getValue).toArray(String[]::new);
    }

    /**
     * get request body
     *
     * @return request body
     */
    public String getBody() {
        String body = null;
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            body = new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return body;
    }

    public <T> T getBody(Class<? extends T> clazz) {
        String body = getBody();
        if (body != null) {
            return serializationAdopter.toObj(body, clazz);
        }
        return null;
    }


    public byte[] getMultipart(String name) {
        try {
            Part part = request.getPart(name);
            return part.getInputStream().readAllBytes();
        } catch (ServletException | IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    // =============================================== http response methods ===============================================
    /**
     * redirect to url
     *
     * @param url redirect url
     */
    public void redirect(String url) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        try {
            response.sendRedirect(url);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void result(String context) {
        try (PrintWriter writer = response.getWriter()) {
            writer.println(context);
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void json(Object obj) {
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            String json = serializationAdopter.toJson(obj);
            writer.println(json);
            writer.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void media(String mediaType, InputStream inputStream) {
        response.setContentType(mediaType);
        try (OutputStream outputStream = response.getOutputStream()) {
            inputStream.transferTo(outputStream);
            outputStream.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
