---
name: notes-conventions
description: Conventions for Spring Boot code in the notes-api — controllers, services, DTOs, the ApiResponse envelope, pagination, archiving instead of deleting. Use whenever asked to add, change or review an endpoint, controller, service, DTO or feature in this Java backend. Trigger phrases - "endpoint", "controller", "service", "DTO", "API", "archive", "delete a note". Do NOT use for frontend, Docker, or CI questions.
---

# notes-conventions

Read the file you are changing first and mirror it. Reference: `src/main/java/com/acme/notes/notes/NoteController.java`.

```java
@RestController
@RequestMapping("/api/<area>")
public class XController {
    private static final int MAX_PAGE_SIZE = 100;
    private final XService service;
    public XController(XService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<XDtos.Summary>> list(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "20") int size) {
        int pageNo = Math.max(page, 1);
        int pageSize = Math.clamp(size, 1, MAX_PAGE_SIZE);
        var result = service.list(pageNo, pageSize);
        return ApiResponse.ok(result.items(), PageMeta.of(pageNo, pageSize, result.total()));
    }
}
```

- New request/response shapes go into the feature's `XDtos` as `record`s; validate with `@Valid`.
- Services own the rules: paging, archiving (never physical deletion), and `ApiException` for domain errors.
- Add a test in `src/test/java` for every new endpoint (MockMvc, asserting the envelope), then run
  `mvn -q -o test` and quote the result in your final message.
