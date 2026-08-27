---
type: regex
target: files
pattern: '\bcatch\s*\('
match: not_contains
---

Domain errors must be signaled with `ApiException` and mapped by `GlobalExceptionHandler`; none of the changed files should contain a `catch` block.
