# Walkthrough — what agent-config-ci did to this repo

This repo started as a plain Spring Boot service with a `CLAUDE.md`, one skill and one hook — the
kind of setup a small team has after a month with Claude Code. Then:

1. `claude plugin marketplace add komo/agent-config-ci && claude plugin install agent-config-ci@komo`
2. In this repo: `claude` → `/agent-config-ci:setup`

The `setup` skill found the setup, wrote eval cases from it under `agent-config/evals/`, smoke-ran
them, and wrote `.github/workflows/agent-config-ci.yml`. The cases it produced, the smoke results,
and what had to be corrected by hand are recorded below as they happened (see the sections appended
by the run log). The suite then runs on every Claude Code release; the first baseline is in
`agent-config/evals/results/`.

What each case proves is in each case's `prompt.md` and `graders/*.md`; the HTML report next to
every result explains every score.
