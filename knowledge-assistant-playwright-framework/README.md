# Knowledge Assistant — Playwright Framework (POM + Data-Driven)

UI automation for [Knowledge Assistant](https://main-knowledge-assistant.newpage.workers.dev/) using **Playwright**, **Page Object Model**, and **JSON data-driven** tests.

## Prerequisites

- Node.js 18+
- npm

## Setup

```bash
cd knowledge-assistant-playwright-framework
npm install
npx playwright install chromium
```

## Run tests

```bash
npm test              # headless
npm run test:headed   # visible browser
npm run test:ui       # Playwright UI mode
npm run report        # open HTML report
```

## Folder structure

```
knowledge-assistant-playwright-framework/
├── playwright.config.ts
├── package.json
├── pages/
│   ├── BasePage.ts
│   └── KnowledgeAssistantPage.ts      # POM with data-testid locators
├── utils/
│   ├── testDataLoader.ts              # Loads JSON test data
│   └── assertions.ts                  # Reusable UI assertions
├── test-data/
│   ├── query-positive.json            # 8 positive query scenarios
│   ├── query-negative.json            # 10 negative / guardrail scenarios
│   ├── documents-positive.json        # 8 positive document panel scenarios
│   └── documents-negative.json        # 8 negative access scenarios
└── tests/
    ├── query-positive.spec.ts
    ├── query-negative.spec.ts
    ├── documents-positive.spec.ts
    └── documents-negative.spec.ts
```

## UI `data-testid` hooks

| Test ID    | Element              |
|------------|----------------------|
| `region`   | Region dropdown      |
| `role`     | Role dropdown        |
| `question` | Question input       |
| `ask`      | Ask button           |
| `answer`   | Answer text area     |
| `cites`    | Citations container  |
| `citation` | Individual citation  |
| `docs`     | Documents panel      |
| `doc`      | Document row         |

## Test coverage (34 tests)

### Query — Positive (8)
- Region/role grounded answers with citations
- Engineering, Finance, Manager role-specific queries
- Special characters, Enter key submit

### Query — Negative (10)
- Empty / whitespace questions
- Refusal when no approved in-scope document
- Out-of-scope role access
- Lifecycle: no In Review, Retired, or Draft citations
- Prompt-injection guardrails

### Documents — Positive (8)
- Correct approved docs per region + role
- Region change reloads panel

### Documents — Negative (8)
- Draft, In Review, Retired never shown
- Role/region access boundaries

## Behavioral rules validated

1. **Access by region and role** — only Global or user region + All Staff or user role
2. **Lifecycle** — only Approved documents in panel and citations
3. **Grounding** — citations present for factual answers
4. **Refusal** — no guessing on unknown topics
5. **Guardrails** — prompt injection cannot reveal restricted content

## Add a new test

Add an entry to the relevant JSON file under `test-data/` — no code change required:

```json
{
  "testCaseId": "UI-Q-POS-009",
  "description": "New scenario",
  "region": "Americas",
  "role": "Employee",
  "question": "What is my meal allowance?",
  "expectedMinCitations": 1
}
```
