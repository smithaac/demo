# Implementation Plan — 02 Data Setup

Spec: `.claude/specs/02-datasetup.md`
Branch: `feature/02-datasetup`

---

## Step 1 — Create `src/main/resources/user.csv`

**File:** `src/main/resources/user.csv` (new file)

One column name per line — no header row, no quotes. These are the canonical user data fields:

```
id
name
email
createdAt
```

Spring Boot automatically includes everything under `src/main/resources` on the classpath, so the file will be accessible via `ClassPathResource("user.csv")` at runtime without any extra configuration.

---

## Step 2 — Create `UserColumnService.java`

**File:** `src/main/java/com/example/demo/UserColumnService.java` (new file)

- Annotated `@Service`
- Inject `ResourceLoader` via constructor
- Method `getColumns()` returns `List<String>`:
  - Load `ClassPathResource("user.csv")` via `ResourceLoader`
  - Read all lines with `BufferedReader` / `Files.readAllLines`
  - Filter out blank lines
  - Return the list
- No caching needed at this stage — file is small and reads are fast

---

## Step 3 — Create `UserColumnController.java`

**File:** `src/main/java/com/example/demo/UserColumnController.java` (new file)

- Annotated `@RestController`
- Inject `UserColumnService` via constructor
- Single method mapped to `GET /user/id` via `@GetMapping("/user/id")`
- Return type `List<String>` — Spring's Jackson integration serializes it as a JSON array automatically
- No error handling needed: the CSV is a static classpath resource that always exists

---

## Step 4 — Create `UserColumnServiceTest.java`

**File:** `src/test/java/com/example/demo/UserColumnServiceTest.java` (new file)

- Plain JUnit 5 test, no Spring context
- Instantiate `UserColumnService` with a real `DefaultResourceLoader` (no mocking needed — the CSV is on the test classpath too)
- Test method `getColumns_returnsExpectedColumns()`:
  - Call `service.getColumns()`
  - Assert the list contains `"id"`, `"name"`, `"email"`, `"createdAt"` in order
  - Assert list size matches

---

## Step 5 — Verify

Run in order:

1. `./mvnw test` — all 3 tests must pass (`DemoApplicationTests`, `HelloControllerTest`, `UserColumnServiceTest`)
2. `./mvnw spring-boot:run` — server starts on port 8080
3. `curl http://localhost:8080/user/id` — confirms JSON array `["id","name","email","createdAt"]`

---

## Change summary

| # | File | Action |
|---|------|--------|
| 1 | `src/main/resources/user.csv` | Create |
| 2 | `src/main/java/com/example/demo/UserColumnService.java` | Create |
| 3 | `src/main/java/com/example/demo/UserColumnController.java` | Create |
| 4 | `src/test/java/com/example/demo/UserColumnServiceTest.java` | Create |

4 new files, no existing files modified, no new dependencies.
