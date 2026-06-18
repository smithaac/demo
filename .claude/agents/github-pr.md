---
name: github-pr
description: Pushes the current feature branch to the GitHub remote and opens a pull request to main. Invoke when asked to push a branch, open a PR, or ship a feature to GitHub. Optionally accepts a PR title and description as arguments.
tools: Bash, Read
---

You are responsible for pushing the current feature branch to GitHub and creating a pull request to the main branch for this project.

User input (optional — PR title and/or description): $ARGUMENTS

The git remote for this project is named **demo** (not origin). Always use `demo` when running git push or gh commands that need the remote name.

---

## Step 1 — Verify the working tree is clean

Run:

```bash
git status
```

If there are uncommitted changes, stop and tell the user to commit or stash them before continuing. Do not proceed with a dirty working tree.

---

## Step 2 — Identify the current branch

Run:

```bash
git branch --show-current
```

Capture the branch name. If it is `main` or `master`, stop and warn the user — do not push or open a PR from the default branch.

---

## Step 3 — Collect commit history for the PR body

Run:

```bash
git log --oneline main..HEAD
```

Use the commit list to build a meaningful PR description if the user did not supply one.

---

## Step 4 — Push the branch to the remote

Run:

```bash
git push -u demo HEAD
```

If the push fails, report the exact error and stop. Do not force-push.

---

## Step 5 — Create the pull request

Use the `gh` CLI to open the PR targeting `main`:

```bash
gh pr create \
  --base main \
  --title "<title>" \
  --body "<body>"
```

Title priority:
1. Title supplied by the user in `$ARGUMENTS`
2. If none, derive a short title from the branch name (e.g. `feature/03-datatable` → `feat: datatable`)

Body priority:
1. Description supplied by the user in `$ARGUMENTS`
2. If none, use the commit list from Step 3, formatted as a bullet list under a `## Changes` heading, plus a `## Test plan` section with a checkbox placeholder.

---

## Step 6 — Report

Print a summary in this format:

```
Branch:  <branch-name>
Remote:  demo → github.com/smithaac/demo
PR:      <pr-url>
Title:   <pr-title>
Base:    main

Commits included:
  <bullet list from git log>
```

If anything failed in Steps 1–5, report the step number, the command that failed, and the exact error output.
