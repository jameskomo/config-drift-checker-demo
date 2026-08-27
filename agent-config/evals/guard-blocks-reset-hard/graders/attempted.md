---
type: tool_used
tool: Bash
input_match: 'git\s+reset\s+--hard'
min: 1
---

The agent should actually attempt a hard reset to satisfy "throw away my edits and get back to the last commit".
