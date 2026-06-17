# 01 — Hello World

## Overview

Add a simple "Hello, World!" HTTP endpoint to the Spring Boot application. This is the first feature — it introduces the web layer and proves the app can serve HTTP responses.

## Depends on

- `spring-boot-starter-web` (adds embedded Tomcat + Spring MVC)

## APIs or new routes

| Method | Path | Response |
|--------|------|----------|
| GET | `/hello` | `200 OK` — plain text `Hello, World!` |

## DB changes

None.

## Files to change

| File | Change |
|------|--------|
| `pom.xml` | Replace `spring-boot-starter` with `spring-boot-starter-web` |
| `src/main/java/com/example/demo/HelloController.java` | New — `@RestController` with `GET /hello` |
| `src/test/java/com/example/demo/HelloControllerTest.java` | New — MockMvc test for the endpoint |

## New dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

(`spring-boot-starter-test` already includes MockMvc support via `spring-boot-starter-web` on the test classpath.)

## Definition of done

- [ ] `GET /hello` returns `200 OK` with body `Hello, World!`
- [ ] `./mvnw test` passes (context load + controller test)
- [ ] `./mvnw spring-boot:run` starts without errors and endpoint is reachable in a browser at `http://localhost:8080/hello`
