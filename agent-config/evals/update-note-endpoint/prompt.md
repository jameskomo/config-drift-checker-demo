---
name: update-note-endpoint
tags: [skill, notes-conventions, real-code]
runs: 3
max_turns: 10
timeout_seconds: 300
allowed_tools: [Read, Glob, Grep, Edit, Write]
---

Users want to be able to fix a typo in a note without deleting and recreating it. Can you add
support for updating an existing note's title and body by id in this notes API?
