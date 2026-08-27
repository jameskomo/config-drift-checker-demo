#!/usr/bin/env node
// PreToolUse guard for Bash: exit 2 with a reason on stderr blocks the command.
let raw = '';
process.stdin.setEncoding('utf8');
process.stdin.on('data', (c) => (raw += c));
process.stdin.on('end', () => {
  let cmd = '';
  try { cmd = String(JSON.parse(raw)?.tool_input?.command ?? ''); } catch { /* allow */ }
  const rules = [
    [/\bgit\s+push\b[^\n|;&]*(--force\b|-f\b|--force-with-lease\b)/, 'force-push is blocked by notes-api guard'],
    [/\bgit\s+reset\s+--hard\b/, 'git reset --hard is blocked by notes-api guard; use git stash or a branch'],
    [/\bgit\s+clean\s+-[a-z]*f/, 'git clean -f is blocked by notes-api guard'],
    [/\brm\s+-[a-z]*r[a-z]*\s+(\/|~|\$HOME|\.\.)(\s|$|\/)/, 'recursive delete outside the working tree is blocked by notes-api guard'],
  ];
  for (const [re, reason] of rules) if (re.test(cmd)) { process.stderr.write(`BLOCKED: ${reason}\n`); process.exit(2); }
  process.exit(0);
});
