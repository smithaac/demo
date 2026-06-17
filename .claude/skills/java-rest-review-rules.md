# Java REST Review Rules

A reference checklist for reviewing Java 17 + Spring Boot REST applications. Apply every section to every review unless the scope is explicitly narrowed.

---

## 1. Java Patterns

### Null safety
- Return `Optional<T>` from service methods that may produce no result; never return raw `null`.
- Guard every `@RequestParam`, `@PathVariable`, and deserialized field that is not `@NotNull`-validated before use.
- Prefer `Objects.requireNonNull` or `Optional.orElseThrow` over silent null propagation.

### Immutability & value objects
- Make DTOs and domain objects immutable where possible (final fields, no setters, or Java records).
- Avoid mutable static state; shared mutable state requires explicit synchronization.

### Exception handling
- Never swallow exceptions with an empty `catch` block.
- Wrap checked exceptions in meaningful unchecked exceptions with a clear message.
- Use `@ExceptionHandler` / `@ControllerAdvice` to translate exceptions into structured HTTP error responses — do not let stack traces reach the client.

### Collections & streams
- Prefer streams + `collect` over manual loops when the intent is a transformation.
- Do not call `.get()` on a stream after `findFirst()` without an `orElseThrow` — it throws `NoSuchElementException` with no context.
- Return unmodifiable collections from service methods (`List.copyOf`, `Collections.unmodifiableList`).

### Resource management
- Wrap `Closeable` resources (streams, connections) in try-with-resources.
- Never hold a database connection or file handle across an HTTP response boundary.

---

## 2. Spring Boot / REST Rules

### Layer separation
- **Controller** — HTTP only: parse request, call service, map result to response. Zero business logic.
- **Service** — all business rules and orchestration. No `HttpServletRequest`, no `ResponseEntity`.
- **Repository** — data access only. No business logic, no HTTP types.

### Dependency injection
- Use **constructor injection** exclusively. Field injection (`@Autowired` on a field) makes testing harder and hides required dependencies.
- Mark injected collaborators `final`; let Lombok `@RequiredArgsConstructor` or an explicit constructor wire them.

### Annotations
| Annotation | Correct use |
|---|---|
| `@RestController` | JSON/XML REST endpoints (implies `@ResponseBody`) |
| `@Controller` | MVC endpoints that return view names |
| `@Service` | Business-logic beans |
| `@Repository` | Data-access beans — also enables Spring's exception translation |
| `@Component` | Generic Spring-managed bean when no other stereotype fits |

- Do not mix stereotypes (e.g., `@Service` on a controller class).
- Use `@RequestMapping` at class level for the base path; use `@GetMapping`, `@PostMapping`, etc. on methods — never `@RequestMapping(method = RequestMethod.GET)` on methods.

### HTTP semantics
| Operation | Method | Success status |
|---|---|---|
| Fetch resource | GET | 200 OK |
| Create resource | POST | 201 Created + `Location` header |
| Full update | PUT | 200 OK or 204 No Content |
| Partial update | PATCH | 200 OK or 204 No Content |
| Delete | DELETE | 204 No Content |

- Return `404 Not Found` when a requested resource does not exist — not `200` with an empty body.
- Return `400 Bad Request` for validation failures, `422 Unprocessable Entity` for semantic errors.
- Never return `500` for a known error condition; map it explicitly.

### Request validation
- Annotate request DTOs with Bean Validation constraints (`@NotNull`, `@Size`, `@Pattern`, etc.).
- Add `@Valid` (or `@Validated`) to the `@RequestBody` / `@ModelAttribute` parameter in the controller.
- Handle `MethodArgumentNotValidException` in a `@ControllerAdvice` to return structured `400` responses.

### Response bodies
- Use dedicated response DTOs — never expose JPA entities directly (avoids lazy-load issues, serialization loops, and over-fetching).
- Exclude `null` fields from JSON output (`@JsonInclude(NON_NULL)`) unless the consumer needs explicit nulls.

### Configuration & externalization
- All environment-specific values (URLs, credentials, feature flags) must come from `application.properties` / `application.yml` or environment variables — never hardcoded.
- Bind grouped properties with `@ConfigurationProperties` + a `@Configuration` class; avoid scattered `@Value` injections.

---

## 3. Security

- **Input sanitization** — validate and reject unexpected characters in path variables and query params before using them in queries or file paths.
- **Authentication & authorization** — every non-public endpoint must be covered by Spring Security. Check that `SecurityFilterChain` is configured; absence means all endpoints are open.
- **Sensitive data** — never log passwords, tokens, PII, or full request bodies containing credentials.
- **CORS** — explicitly configure allowed origins; do not use `allowedOrigins("*")` in production.
- **Actuator** — if `spring-boot-actuator` is on the classpath, restrict `/actuator/**` endpoints; `/actuator/env` and `/actuator/heapdump` leak credentials and memory.

---

## 4. Testing

- Each public service method needs at least one unit test covering the happy path and one covering the main failure path.
- Controller tests should use `@WebMvcTest` + `MockMvc` — not `@SpringBootTest` (too heavy for unit-level controller logic).
- Do not test implementation details (private method calls, exact SQL); test observable behaviour (return values, HTTP status, side effects).
- Use `@ParameterizedTest` for methods with multiple input variants instead of copy-pasting test methods.
- Assert HTTP status, response body structure, and relevant headers — not just that the call didn't throw.

---

## 5. Output format

For every finding, write exactly:

```
**<file>:<line>** | <Critical|Warning|Info> | <issue> | <suggested fix>
```

Group findings by file. After all findings, write a 2–3 sentence overall summary. If a file has no issues, state "No issues found." — never silently skip it.
