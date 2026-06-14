# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: documents-positive.spec.ts >> UI-D-POS-008: Changing region reloads visible documents
- Location: tests\documents-positive.spec.ts:9:7

# Error details

```
Error: expect(received).toContain(expected) // indexOf

Expected value: "D-002"
Received array: ["D-001", "D-004"]
```

# Page snapshot

```yaml
- generic [active] [ref=e1]:
  - banner [ref=e2]:
    - heading "Knowledge Assistant" [level=1] [ref=e3]
    - generic [ref=e4]:
      - generic [ref=e5]: Region
      - combobox [ref=e6]:
        - option "Americas"
        - option "EMEA" [selected]
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
        - textbox "Ask a question, e.g. what is my meal allowance?" [ref=e12]
        - button "Ask" [ref=e13] [cursor=pointer]
      - generic [ref=e14]: Answers will appear here.
    - complementary [ref=e15]:
      - heading "Documents visible to you" [level=2] [ref=e16]
      - generic [ref=e17]:
        - generic [ref=e18]:
          - generic [ref=e19]: Travel and Expense Policy (Americas)
          - generic [ref=e20]: D-001 · Approved
        - generic [ref=e21]:
          - generic [ref=e22]: Remote Work Policy
          - generic [ref=e23]: D-004 · Approved
```

# Test source

```ts
  1  | import { expect, test } from '@playwright/test';
  2  | import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
  3  | import { assertDocumentsPanel } from '../utils/assertions';
  4  | import { loadTestData } from '../utils/testDataLoader';
  5  | 
  6  | const testCases = loadTestData('documents-positive.json');
  7  | 
  8  | for (const data of testCases) {
  9  |   test(`${data.testCaseId}: ${data.description}`, async ({ page }) => {
  10 |     const kaPage = new KnowledgeAssistantPage(page);
  11 |     await kaPage.open();
  12 | 
  13 |     if (data.testCaseId === 'UI-D-POS-008') {
  14 |       await kaPage.selectContext('Americas', 'Employee');
  15 |       await assertDocumentsPanel(kaPage, {
  16 |         ...data,
  17 |         expectedDocumentIds: ['D-001'],
  18 |       });
  19 | 
  20 |       await kaPage.selectContext('EMEA', 'Employee');
  21 |       const emeaIds = await kaPage.getVisibleDocumentIds();
> 22 |       expect(emeaIds).toContain('D-002');
     |                       ^ Error: expect(received).toContain(expected) // indexOf
  23 |       expect(emeaIds).not.toContain('D-001');
  24 |       return;
  25 |     }
  26 | 
  27 |     await kaPage.selectContext(data.region!, data.role!);
  28 |     await assertDocumentsPanel(kaPage, data);
  29 |   });
  30 | }
  31 | 
```