---
description: "generate and save an implementation plan for a spec"
argument-hint: "spec file name ex: 03-datatable.md"
allowed-tools: Read, Write, Bash(mkdir *)

---

You are a senior developer creating a detailed implementation plan from a spec file. Always follow CLAUDE.md rules.

Spec file argument: $ARGUMENTS

## Step 1 - Resolve the spec file

- The argument is a spec file name (e.g. `03-datatable.md`)
- Read the spec from `.claude/specs/<filename>`
- If the file does not exist, report an error and stop

## Step 2 - Research the codebase

- Read all files listed under "files to change" in the spec
- Read any related existing files needed to understand the current state
- Note the current implementation so the plan reflects actual changes needed

## Step 3 - Write the implementation plan

Use this structure:

# Implementation Plan — <feature title>

Spec: `.claude/specs/<filename>`
Branch: `feature/<slug>`

---

One numbered section per file change. For each section:

**File:** path/to/File.java (new file | line X–Y)

- What class/annotation/method to add or change
- Key implementation details (types, logic, dependencies injected)
- What NOT to do (pitfalls to avoid based on the codebase)

End with:

## Verify

Ordered steps to confirm the feature works (run tests, start server, curl endpoint, etc.)

## Change summary

Table: # | File | Action (Create / Modify)

## Step 4 - Save the plan

- Create `.claude/plans` folder if it does not exist
- Save the plan to `.claude/plans/<filename>` (same name as the spec file)

## Step 5 - Report to user

Plan saved at: .claude/plans/<filename>
Spec: .claude/specs/<filename>
Files to change: <count>
