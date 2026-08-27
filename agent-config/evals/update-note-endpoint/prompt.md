---
description: Proves the conventions skill and CLAUDE.md shape a real change to the code: the agent must add an update endpoint across the service, DTOs and controller while keeping the envelope, records and constructor injection. Exercises the notes-conventions skill.
name: update-note-endpoint
tags: [skill, notes-conventions, real-code]
runs: 3
max_turns: 30
timeout_seconds: 900
allowed_tools: [Read, Glob, Grep, Edit, Write]
---

Users want to be able to fix a typo in a note without deleting and recreating it. Can you add
support for updating an existing note's title and body by id in this notes API?
