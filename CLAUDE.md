# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run the application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=DemoApplicationTests

# Run a single test method
./mvnw test -Dtest=DemoApplicationTests#contextLoads
```

## Architecture

This is a Spring Boot 4.1.0 application targeting Java 17. The project is a generated starter with no web layer, database, or business logic yet — it consists only of the application entry point (`DemoApplication`) and a context-load smoke test.

- `src/main/java/com/example/demo/` — application source root
- `src/test/java/com/example/demo/` — test root
- `src/main/resources/application.properties` — runtime configuration (currently only sets `spring.application.name=demo`)

Dependencies include only `spring-boot-starter` (core autoconfiguration, no web) and `spring-boot-starter-test` (JUnit 5 + Spring test support).
