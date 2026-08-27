# notes-api — working rules for coding agents

Small Spring Boot 3.5 / Java 21 service. These rules are cheap to break and expensive to find later.

## Code
- Every endpoint returns the envelope `ApiResponse` — `ok(data)`, `ok(items, PageMeta.of(page, size, total))`,
  `fail(code, message)`. Never a bare list, entity or `Map`.
- Pagination is 1-based `page` and `size`: `Math.max(page, 1)` and `Math.clamp(size, 1, MAX_PAGE_SIZE)`
  with `private static final int MAX_PAGE_SIZE = 100`.
- DTOs are `record`s grouped in `<Feature>Dtos`. Dependencies are `private final` fields set by an
  explicit constructor. No Lombok, no field `@Autowired` in production code.
- Domain errors: throw `ApiException(status, CODE, message)`; `GlobalExceptionHandler` maps it. No try/catch in controllers.
- Notes are never deleted. "Delete" means archive (`archived = true`); listings exclude archived notes.

## Workflow
- Before you say a change is done, run `mvn -q -o test` and report the result. If it fails, fix it first.
- Never `git push --force`, `git reset --hard`, or `git clean -f` in this repo.
