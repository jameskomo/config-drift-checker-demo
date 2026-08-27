---
type: regex
target: files
flags: m
pattern: '^### (?!.*(NoteController\.java|NoteService\.java|NoteDtos\.java|NoteApiTest\.java)$).+$'
match: not_contains
---

Only `NoteController.java`, `NoteService.java`, `NoteDtos.java`, and optionally `NoteApiTest.java` should be touched for this feature.
