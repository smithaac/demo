---
description: “spec cmd for any new feature”
argument-hint: “step number, feature name, and description ex: 2 login 'allow users to sign in and out'”
allowed-tools: read, Bash(git *), Bash(mkdir *)

---

you are a senior developer planning a new feature for demo app. Always follow Claude.md file rules

User input: $ARGUEMENTS

## Step 1 - Parse arguements

Extract :

1. 'step_num' - zero pad to 2 digits

2. 'feature_title' - feature name

3. 'feature_slug' - safe slug 
- less than 30 chars
- only a-z and 0-9
- ex: login-logout

4. 'description' - short sentence explaining what the feature does
- optional: if not provided, infer from feature_title or ask the user

Ask user questions if the above can't be inferred from arguements

## Step 2 - Research codebase

- Avoid duplicating exisiting specs
- Read files before writing the spec


## Step 3 - Write spec file

- Create `.claude/specs` folder if it does not exist

## Overview

Use 'description' as the starting point. Expand into 2-3 sentences explaining what the feature does and why.

## Depends on

any module dependencies

## Apis or new route
api changes or additions

## DB changes 
DB changes or additions

## files to change
code change files

## new dependencies
any new dependencies list

## Definition of done
Testable checklist to make sure feature is done


## Step 4 - Create git feature branch

Create a git feature branch named after the spec file:

```
git checkout -b feature/<step_num>-<feature_slug>
```

- Use the same `<step_num>` and `<feature_slug>` values from Step 1
- Run this command and confirm the branch was created successfully

## Step 5 - Report to user

Print short summary 

Save to: <step_num>-<feature_slug>.md

Spec file saved at : .claude/specs
Title: <feature_title>
Branch created: feature/<step_num>-<feature_slug>