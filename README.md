# Trello API Automation

REST API test automation for the [Trello API](https://developer.atlassian.com/cloud/trello/rest/) using **RestAssured**, **TestNG**, **Jackson**, and **Allure**.

This project demonstrates end-to-end API test automation: creating real resources on Trello's servers, chaining requests (extracting an ID from one response to drive the next), verifying that created data actually exists, and cleaning up afterward so no test data is left behind.

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java | Language |
| Maven | Build & dependency management |
| RestAssured | HTTP requests & response assertions |
| TestNG | Test runner, annotations, dependencies |
| Jackson | JSON serialization / deserialization |
| Allure | Test reporting |

---

## What the tests cover

The suite follows the Trello data hierarchy: **Board → List → Card**.

| Test | Type | What it verifies |
|------|------|------------------|
| `getMembersMe` | Smoke | Auth works; `GET /members/me` returns `200` |
| `createBoard` | Create | A board is created; ID is returned |
| `createList` | Create + chaining | A list is created **on the created board** (`idBoard` chaining) |
| `createCard` | Create + chaining | A card is created **in the created list** (`idList` chaining) |
| `getListsShouldContainCreatedList` | Read / verify | The created list actually exists on the board (`hasItem`) |

Tests run in dependency order via TestNG `dependsOnMethods`. A board-level `@AfterClass` teardown deletes the board, which cascades to remove all lists and cards — leaving no test data behind.

---

## Setup

### 1. Get your Trello API credentials

Generate a key and token from the [Trello Power-Up admin page](https://trello.com/power-ups/admin).

### 2. Set them as environment variables

Credentials are **never** committed to the repo. They are read at runtime from environment variables via the `ConfigReader` utility class.

**Windows (PowerShell):**
```powershell
setx TRELLO_KEY "your_key_here"
setx TRELLO_TOKEN "your_token_here"
```

**macOS / Linux:**
```bash
export TRELLO_KEY="your_key_here"
export TRELLO_TOKEN="your_token_here"
```

> If a variable is missing, the test suite fails fast with a clear message instead of sending an invalid request.

---

## Running the tests

```bash
mvn clean test
```

## Viewing the Allure report

```bash
allure serve allure-results
```

---

## Project structure

```
src/
├── main/java/com/tamarbunturi/trello/
│   └── config/
│       └── ConfigReader.java      # reads TRELLO_KEY / TRELLO_TOKEN from env
└── test/java/com/tamarbunturi/trello/
    └── tests/
        ├── MembersTest.java       # auth smoke test
        └── BoardTest.java         # board → list → card lifecycle + verification
```

---

## Author

**Tamar Bunturi** - QA Engineer (ISTQB CTFL)
