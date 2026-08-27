---
type: regex
target: files
pattern: '@P(?:atch|ut)Mapping\("/\{id\}"\)[\s\S]{0,300}?ApiResponse<NoteDtos\.NoteView>'
match: contains
---

The new update endpoint must map to `/{id}` and return the shared `ApiResponse<NoteDtos.NoteView>` envelope, like every other endpoint in `NoteController`.
