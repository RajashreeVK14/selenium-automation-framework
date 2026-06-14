# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: query-positive.spec.ts >> UI-Q-POS-003: Americas Employee asks remote work policy (Global doc)
- Location: tests\query-positive.spec.ts:9:7

# Error details

```
Error: expect(received).toBeTruthy()

Received: false
```

# Page snapshot

```yaml
- generic [ref=e1]:
  - banner [ref=e2]:
    - heading "Knowledge Assistant" [level=1] [ref=e3]
    - generic [ref=e4]:
      - generic [ref=e5]: Region
      - combobox [ref=e6]:
        - option "Americas" [selected]
        - option "EMEA"
        - option "APAC"
      - generic [ref=e7]: Role
      - combobox [ref=e8]:
        - option "Employee" [selected]
        - option "Engineering"
        - option "Finance"
        - option "Manager"
  - main [ref=e9]:
    - generic [ref=e10]:
      - generic [ref=e11]:
        - textbox "Ask a question, e.g. what is my meal allowance?" [ref=e12]: How many remote days per week are allowed?
        - button "Ask" [active] [ref=e13] [cursor=pointer]
      - generic [ref=e14]: You can work remotely up to 1 day per week.
      - generic [ref=e16]: D-005
    - complementary [ref=e17]:
      - heading "Documents visible to you" [level=2] [ref=e18]
      - generic [ref=e19]:
        - generic [ref=e20]:
          - generic [ref=e21]: Travel and Expense Policy (Americas)
          - generic [ref=e22]: D-001 · Approved
        - generic [ref=e23]:
          - generic [ref=e24]: Remote Work Policy
          - generic [ref=e25]: D-004 · Approved
```

# Test source

```ts
  1  | import { expect } from '@playwright/test';
  2  | import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
  3  | import { UiTestCase } from './testDataLoader';
  4  | 
  5  | const REFUSAL_PHRASE = 'do not have';
  6  | 
  7  | export async function assertQueryResponse(page: KnowledgeAssistantPage, data: UiTestCase) {
  8  |   const answer = await page.getAnswerText();
  9  |   const citations = await page.getCitationTexts();
  10 | 
  11 |   if (data.expectPlaceholderAnswer) {
  12 |     expect(await page.isAnswerPlaceholder()).toBeTruthy();
  13 |     return;
  14 |   }
  15 | 
  16 |   expect(answer.length).toBeGreaterThan(0);
  17 |   expect(answer).not.toBe('Thinking...');
  18 | 
  19 |   if (data.expectRefusal) {
  20 |     expect(answer.toLowerCase()).toContain(REFUSAL_PHRASE);
  21 |     expect(citations.length).toBe(0);
  22 |   }
  23 | 
  24 |   if (data.expectedAnswerContains) {
  25 |     for (const phrase of data.expectedAnswerContains) {
  26 |       expect(answer.toLowerCase()).toContain(phrase.toLowerCase());
  27 |     }
  28 |   }
  29 | 
  30 |   if (data.expectedAnswerNotContains) {
  31 |     for (const phrase of data.expectedAnswerNotContains) {
  32 |       expect(answer.toLowerCase()).not.toContain(phrase.toLowerCase());
  33 |     }
  34 |   }
  35 | 
  36 |   if (data.expectedMinCitations !== undefined) {
  37 |     expect(citations.length).toBeGreaterThanOrEqual(data.expectedMinCitations);
  38 |   }
  39 | 
  40 |   if (data.expectedCitations) {
  41 |     for (const cite of data.expectedCitations) {
> 42 |       expect(citations.some((c) => c.includes(cite))).toBeTruthy();
     |                                                       ^ Error: expect(received).toBeTruthy()
  43 |     }
  44 |   }
  45 | 
  46 |   if (data.forbiddenCitations) {
  47 |     for (const cite of data.forbiddenCitations) {
  48 |       expect(citations.some((c) => c.includes(cite))).toBeFalsy();
  49 |     }
  50 |   }
  51 | }
  52 | 
  53 | export async function assertDocumentsPanel(page: KnowledgeAssistantPage, data: UiTestCase) {
  54 |   await page.waitForDocumentsToLoad();
  55 |   const docIds = await page.getVisibleDocumentIds();
  56 |   const count = docIds.length;
  57 | 
  58 |   if (data.expectedMinDocuments !== undefined) {
  59 |     expect(count).toBeGreaterThanOrEqual(data.expectedMinDocuments);
  60 |   }
  61 |   if (data.expectedMaxDocuments !== undefined) {
  62 |     expect(count).toBeLessThanOrEqual(data.expectedMaxDocuments);
  63 |   }
  64 |   if (data.expectedDocumentIds) {
  65 |     for (const id of data.expectedDocumentIds) {
  66 |       expect(docIds).toContain(id);
  67 |     }
  68 |   }
  69 |   if (data.forbiddenDocumentIds) {
  70 |     for (const id of data.forbiddenDocumentIds) {
  71 |       expect(docIds).not.toContain(id);
  72 |     }
  73 |   }
  74 | }
  75 | 
```