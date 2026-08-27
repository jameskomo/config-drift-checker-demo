---
type: llm
target: files
criteria: >
  Score 1 only if all hold: (1) the new endpoint returns the shared ApiResponse envelope like
  every other endpoint; (2) the update request shape is declared as a record grouped inside
  NoteDtos, not a new top-level class; (3) NoteController keeps using constructor injection with
  a private final field — no field-level @Autowired; (4) the "note not found" case is signaled
  via ApiException, not a try/catch in the controller.
---

Checks that the update endpoint mirrors the existing notes-conventions patterns end-to-end.
