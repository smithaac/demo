# 03 — Data Table

## Overview

Restructure `user.csv` from a single-column list into a proper DB-table format: a header row of comma-separated column names followed by one or more data rows of comma-separated values. This aligns the file with how relational tables are typically represented and makes it possible to return structured row objects rather than a flat list of column names. The service and API response are updated to reflect the richer format.

## Depends on

- Builds directly on `02-datasetup` — modifies `user.csv`, `UserColumnService`, and `UserColumnController` introduced there.
- No new library dependencies; CSV parsing uses Java standard library.

## APIs or new routes

Existing endpoint updated (no new routes):

| Method | Path | Old response | New response |
|--------|------|-------------|--------------|
| GET | `/user/id` | `["id","name","email","createdAt"]` | `[{"id":"1","name":"Alice","email":"alice@example.com","createdAt":"2024-01-01"}]` — array of row objects keyed by header |

## DB changes

None — file-based only.

## Files to change

| File | Action |
|------|--------|
| `src/main/resources/user.csv` | Modify — add header row + at least one sample data row in CSV format |
| `src/main/java/com/example/demo/UserColumnService.java` | Modify — parse header + data rows; return `List<Map<String, String>>` instead of `List<String>` |
| `src/main/java/com/example/demo/UserColumnController.java` | Modify — update return type to `List<Map<String, String>>` |
| `src/test/java/com/example/demo/UserColumnServiceTest.java` | Modify — update assertions to verify row maps instead of column name list |

## New dependencies

None.

## Definition of done

- [ ] `user.csv` has a header row (`id,name,email,createdAt`) and at least one data row
- [ ] `GET /user/id` returns a JSON array of objects, each key matching a header column
- [ ] `UserColumnService` derives keys from the CSV header row (not hardcoded)
- [ ] Adding a new column to the CSV header and data rows is reflected in the API without a code change
- [ ] `./mvnw test` passes (all existing tests + updated service test)
