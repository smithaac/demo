# 02 — Data Setup

## Overview

Add a data file to the `src/main/resources` folder that defines the columns of a user entity. This gives the application a canonical, readable definition of user data fields that can be loaded at runtime or used by other features (e.g. validation, serialization, documentation). It establishes the data contract for the user concept before any database or persistence layer is introduced.

## Depends on

- No new dependencies — uses only Java standard library (`java.io`, `java.util`) and Spring's `ResourceLoader` / `ClassPathResource` for reading the file.
- Builds on the running Spring Boot app introduced in `01-helloworld`.

## APIs or new routes

| Method | Path | Response |
|--------|------|----------|
| GET | `/user/id` | `200 OK` — JSON array of column name strings |

## DB changes

None — this feature uses a static resource file, not a database.

## Files to change

| File | Action |
|------|--------|
| `src/main/resources/user.csv` | New — one column name per line (e.g. `id`, `name`, `email`, `createdAt`) |
| `src/main/java/com/example/demo/UserColumnService.java` | New — reads `user.csv` from the classpath and returns the list |
| `src/main/java/com/example/demo/UserColumnController.java` | New — `@RestController` exposing `GET /user/id` |
| `src/test/java/com/example/demo/UserColumnServiceTest.java` | New — unit test verifying the service parses the CSV correctly |

## New dependencies

None.

## Definition of done

- [ ] `src/main/resources/user.csv` exists and contains at least: `id`, `name`, `email`, `createdAt`
- [ ] `GET /user/id` returns `200 OK` with a JSON array matching the file contents
- [ ] `UserColumnService` reads from the classpath file (not a hardcoded list)
- [ ] `./mvnw test` passes (all existing tests + new service unit test)
- [ ] Adding or removing a column in the CSV is reflected in the API response without a code change
