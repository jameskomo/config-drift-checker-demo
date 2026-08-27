---
description: Proves the backend skill does not fire on an unrelated request (a Dockerfile) while the agent still does the job. Exercises the skill's trigger description as a negative case.
name: negative-dockerfile-request
tags: [skill, negative]
runs: 3
max_turns: 12
timeout_seconds: 180
allowed_tools: [Read, Glob, Grep, Write, Edit]
---

Can you add a Dockerfile so this service can be built and run in a container?
