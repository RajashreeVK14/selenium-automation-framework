import { test } from '@playwright/test';
import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
import { assertDocumentsPanel } from '../utils/assertions';
import { loadTestData } from '../utils/testDataLoader';

const testCases = loadTestData('documents-negative.json');

for (const data of testCases) {
  test(`${data.testCaseId}: ${data.description}`, async ({ page }) => {
    const kaPage = new KnowledgeAssistantPage(page);
    await kaPage.open();
    await kaPage.selectContext(data.region!, data.role!);
    await assertDocumentsPanel(kaPage, data);
  });
}
