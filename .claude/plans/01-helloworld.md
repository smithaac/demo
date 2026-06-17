# Implementation Plan — 01 Hello World

Spec: `.claude/specs/01-helloworld.md`
Branch: `feature/01-helloworld`

---

## Step 1 — Swap the starter dependency in `pom.xml`

**File:** `pom.xml` (line 34–36)

Replace the existing `spring-boot-starter` dependency with `spring-boot-starter-web`. This pulls in Spring MVC and an embedded Tomcat server. No version needed — it is managed by the `spring-boot-starter-parent` BOM already declared as the parent.

```
Before: spring-boot-starter
After:  spring-boot-starter-web
```

No other `pom.xml` changes required. `spring-boot-starter-test` (already present) bundles MockMvc support once the web starter is on the compile classpath.

---

## Step 2 — Create `HelloController.java`

**File:** `src/main/java/com/example/demo/HelloController.java` (new file)

- Package: `com.example.demo` (matches the existing source root)
- Annotation: `@RestController` — combines `@Controller` + `@ResponseBody`, so the return value is written directly as the HTTP response body
- Method: mapped to `GET /hello` via `@GetMapping("/hello")`
- Return type: `String` — Spring will write it as `text/plain`
- Return value: `"Hello, World!"`

No service layer, no constructor injection, no additional beans needed for this feature.

---

## Step 3 — Create `HelloControllerTest.java`

**File:** `src/test/java/com/example/demo/HelloControllerTest.java` (new file)

- Package: `com.example.demo`
- Test slice: `@WebMvcTest(HelloController.class)` — loads only the MVC layer, not the full application context; faster than `@SpringBootTest`
- Inject: `MockMvc` via `@Autowired`
- Test method: `getHello_returnsOkAndBody()`
  - Perform `GET /hello`
  - Assert HTTP status `200 OK`
  - Assert response body equals `"Hello, World!"`

The existing `DemoApplicationTests` context-load test is unaffected and must still pass.

---

## Step 4 — Verify

Run in order:

1. `./mvnw test` — both `DemoApplicationTests` and `HelloControllerTest` must pass
2. `./mvnw spring-boot:run` — server starts on port 8080
3. `curl http://localhost:8080/hello` or browser — confirms `Hello, World!` in the response body

---

## Change summary

| # | File | Action |
|---|------|--------|
| 1 | `pom.xml` | Swap `spring-boot-starter` → `spring-boot-starter-web` |
| 2 | `src/main/java/com/example/demo/HelloController.java` | Create |
| 3 | `src/test/java/com/example/demo/HelloControllerTest.java` | Create |

3 files, no DB changes, no new beans beyond the controller itself.
