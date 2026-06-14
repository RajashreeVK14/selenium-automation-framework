import { test } from '@playwright/test';
import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
import { assertQueryResponse } from '../utils/assertions';
import { loadTestData } from '../utils/testDataLoader';

const testCases = loadTestData('query-positive.json');

for (const data of testCases) {
  test(`${data.testCaseId}: ${data.description}`, async ({ page }) => {
    const kaPage = new KnowledgeAssistantPage(page);
    await kaPage.open();
    await kaPage.selectContext(data.region!, data.role!);

    if (data.useEnterKey) {
      await kaPage.pressEnterToAsk(data.question!);
    } else {
      await kaPage.askQuestion(data.question!);
    }

    await assertQueryResponse(kaPage, data);
  });
}
