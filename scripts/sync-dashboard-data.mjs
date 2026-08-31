#!/usr/bin/env node
// Copies the newest agent-config/evals/results/<ts>/aggregate-result.json to
// docs/dashboard/data.json so the Vue dashboard has something to fetch.
import { readdirSync, statSync, copyFileSync, existsSync } from "node:fs";
import { join, dirname } from "node:path";
import { fileURLToPath } from "node:url";

const root = dirname(dirname(fileURLToPath(import.meta.url)));
const resultsDir = join(root, "agent-config/evals/results");
const outFile = join(root, "docs/dashboard/data.json");

const runs = readdirSync(resultsDir, { withFileTypes: true })
  .filter((e) => e.isDirectory())
  .map((e) => e.name)
  .filter((name) => existsSync(join(resultsDir, name, "aggregate-result.json")))
  .sort();

if (runs.length === 0) {
  console.error(`No aggregate-result.json found under ${resultsDir}`);
  process.exit(1);
}

const latest = runs[runs.length - 1];
copyFileSync(join(resultsDir, latest, "aggregate-result.json"), outFile);
console.log(`Synced ${latest} -> docs/dashboard/data.json`);
