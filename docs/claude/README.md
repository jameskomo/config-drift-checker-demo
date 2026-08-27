# Walkthrough — what config-drift-checker did to this repo

This repo started as a plain Spring Boot service with a `CLAUDE.md`, one skill and one hook — the
kind of setup a small team has after a month with Claude Code. Then:

1. `claude plugin marketplace add jameskomo/config-drift-checker && claude plugin install config-drift-checker@jameskomo`
2. In this repo: `claude` → `/config-drift-checker:setup`

The `setup` skill found the setup, wrote eval cases from it under `agent-config/evals/`, smoke-ran
them, and wrote `.github/workflows/config-drift-checker.yml`. The cases it produced, the smoke results,
and what had to be corrected by hand are recorded below as they happened (see the sections appended
by the run log). The suite then runs on every Claude Code release; the first baseline is in
`agent-config/evals/results/`.

What each case proves is in each case's `prompt.md` and `graders/*.md`; the HTML report next to
every result explains every score.

## Run log — 2026-08-27, `/config-drift-checker:setup`, unattended

Invocation (headless, as a CI job would run it):
```
claude -p "/config-drift-checker:setup" --plugin-dir <config-drift-checker plugin> --permission-mode acceptEdits \
  --allowedTools "Bash(node *)" "Bash(git *)" "Bash(mvn *)" … Read Glob Grep Write Edit --max-turns 90 --model sonnet
```
Outcome: 78 turns · 13 min · $1.68 · exit 0.

What it found: `.claude-plugin/plugin.json`, the `notes-conventions` skill, `guard.mjs`, `CLAUDE.md`;
`.claude/` gitignored → kept everything under `agent-config/`.

What it generated (`agent-config/evals/`):
- `update-note-endpoint` — real-code: scaffold copies `src/` + `CLAUDE.md`; asks for a not-yet-existing
  "update a note" feature; graders: envelope + mapping, no try/catch, only expected files changed, skill used
  (indicator), LLM rubric on the diff.
- `negative-dockerfile-request` — a Docker request that must **not** trigger the backend skill, plus a
  check that the Dockerfile was actually created.
- `guard-blocks-reset-hard` — scratch git repo with an uncommitted edit; asks for `git reset --hard`;
  asserts attempted, blocked, not succeeded.

What it repaired by itself (two smoke iterations): the hook prompt was vague enough that the model
picked `git checkout --` instead of the guarded command → rewritten so the exact command is requested;
an LLM grader was reading the reply instead of the diff → `target: files`. Final smoke: 1.00 on 2/2
runs for all three cases.

What it could not do, and said so: no `gh`/remote → secrets left to the owner; the plugin's CI
template was not a straight fit → it adapted (the template has since been made generic and the
workflow here replaced with it).
