# Implementation Plan — Data Table

Spec: `.claude/specs/03-datatable.md`
Branch: `feature/03-datatable`

---

## 1. `src/main/resources/user.csv` (Modify — line 1–4)

**File:** `src/main/resources/user.csv` (modify, currently lines 1–4)

- Replace the single-column-per-line format with a proper CSV layout: a comma-separated header row followed by at least one comma-separated data row.
- Header row must be: `id,name,email,createdAt`
- Add one sample data row: `1,Alice,alice@example.com,2024-01-01`
- Do NOT keep newline-separated column names — that format is being replaced entirely.

```
id,name,email,createdAt
1,Alice,alice@example.com,2024-01-01
```

---

## 2. `src/main/java/com/example/demo/UserColumnService.java` (Modify — full file)

**File:** `src/main/java/com/example/demo/UserColumnService.java`

- Rename `getColumns()` → `getRows()` and change return type from `List<String>` to `List<Map<String, String>>`.
- Add imports: `java.util.ArrayList`, `java.util.LinkedHashMap`, `java.util.Map`.
- Remove imports no longer needed: `java.util.stream.Collectors` (can still use, but logic is simpler with a manual loop).
- Parsing logic:
  1. Read all non-empty trimmed lines from `classpath:user.csv`.
  2. First line → split by `","` → `String[] headers`.
  3. For each remaining line → split by `","` → zip with `headers` into a `LinkedHashMap<String, String>` (preserves column order), add to result list.
- Return the result list.
- Do NOT hardcode column names anywhere — all keys must derive from the header row at runtime so that adding a column to the CSV is reflected automatically.
- Do NOT use a CSV library; plain `String.split(",")` is sufficient for this flat, unquoted format.

```java
public List<Map<String, String>> getRows() {
    try (BufferedReader reader = ...) {
        List<String> lines = reader.lines()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
        if (lines.isEmpty()) return List.of();
        String[] headers = lines.get(0).split(",");
        List<Map<String, String>> rows = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            Map<String, String> row = new LinkedHashMap<>();
            for (int j = 0; j < headers.length; j++) {
                row.put(headers[j].trim(), j < values.length ? values[j].trim() : "");
            }
            rows.add(row);
        }
        return rows;
    } catch (Exception e) {
        throw new RuntimeException("Failed to read user.csv", e);
    }
}
```

---

## 3. `src/main/java/com/example/demo/UserColumnController.java` (Modify — full file)

**File:** `src/main/java/com/example/demo/UserColumnController.java`

- Change the return type of `getUserColumns()` from `List<String>` to `List<Map<String, String>>`.
- Update the call from `userColumnService.getColumns()` → `userColumnService.getRows()`.
- Add import: `java.util.Map`.
- The `@GetMapping("/user/id")` path stays the same — no new routes.
- Do NOT change the endpoint path or add `@ResponseBody` (already implicit via `@RestController`).

---

## 4. `src/test/java/com/example/demo/UserColumnServiceTest.java` (Modify — full file)

**File:** `src/test/java/com/example/demo/UserColumnServiceTest.java`

- Update test method name from `getColumns_returnsExpectedColumns` → `getRows_returnsExpectedRows`.
- Change local variable type from `List<String>` to `List<Map<String, String>>`.
- Call `service.getRows()` instead of `service.getColumns()`.
- Add import: `java.util.Map`.
- Update assertions:
  - Assert list has size 1.
  - Assert the first element is a map containing the expected key-value pairs: `id=1`, `name=Alice`, `email=alice@example.com`, `createdAt=2024-01-01`.
- Do NOT assert `containsExactly` with bare strings — the return type is now a list of maps.

```java
@Test
void getRows_returnsExpectedRows() {
    List<Map<String, String>> rows = service.getRows();
    assertThat(rows).hasSize(1);
    assertThat(rows.get(0))
            .containsEntry("id", "1")
            .containsEntry("name", "Alice")
            .containsEntry("email", "alice@example.com")
            .containsEntry("createdAt", "2024-01-01");
}
```

---

## Verify

1. `./mvnw test` — all tests must pass, including the updated `UserColumnServiceTest`.
2. `./mvnw spring-boot:run` — start the server.
3. `curl http://localhost:8080/user/id` — response must be:
   ```json
   [{"id":"1","name":"Alice","email":"alice@example.com","createdAt":"2024-01-01"}]
   ```
4. Add a second data row to `user.csv` (e.g. `2,Bob,bob@example.com,2024-02-01`) without touching Java code, then re-curl — the response must include both rows, confirming the header-driven approach works.

---

## Change summary

| # | File | Action |
|---|------|--------|
| 1 | `src/main/resources/user.csv` | Modify |
| 2 | `src/main/java/com/example/demo/UserColumnService.java` | Modify |
| 3 | `src/main/java/com/example/demo/UserColumnController.java` | Modify |
| 4 | `src/test/java/com/example/demo/UserColumnServiceTest.java` | Modify |
