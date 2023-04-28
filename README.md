# <img src=logo.png width=100 height=100/> Revali

![gradle build](https://github.com/fe1fan/revali/actions/workflows/gradle.yml/badge.svg)
![size](https://img.shields.io/github/languages/code-size/fe1fan/revali)
![last commit](https://img.shields.io/github/last-commit/fe1fan/revali)
![license](https://img.shields.io/github/license/fe1fan/revali)

A fast and simple web framework for Java.

**⚠️ This Repository is still under development, the api may change at any time. ⚠️**

### Modules

- [revali-core](revali-core) - The core module of revali, provides some common tools.
- [revali-server](revali-server) - The core module of revali, provides a simple web server.
- [revali-client](revali-client) - A simple http client for revali.
- [revali-data](revali-data) - A simple data access layer for revali.

### Usage with Gradle

```groovy
implementation 'io.xka.revali:revali-[model]:[version]'
```

### Usage with Maven

```xml
<dependency>
    <groupId>io.xka.revali</groupId>
    <artifactId>revali-[model]</artifactId>
    <version>[version]</version>
</dependency>
```

### Example

```java
import io.xka.revali.server.RevaliServer;
import io.xka.revali.server.RevaliServerBuilder;
import io.xka.revali.server.RevaliServerControl;

public class Example {

    public static void main(String[] args) {
        // create a server from yaml file
        RevaliServer server = RevaliServerBuilder.yaml("bootstrap.yaml");
        // start the server
        RevaliServerControl startup = server.startup();
        // add a get route
        startup.get("/test", control -> {
            control.result("hello world");
        });
        // add a post route
        startup.get("/json", control -> {
            control.json(
                    new HashMap<>() {
                        {
                            put("hello", "world");
                        }
                    }
            );
        });
    }
}
```
