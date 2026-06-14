import { expect, test } from '@playwright/test';
import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
import { assertDocumentsPanel } from '../utils/assertions';
import { loadTestData } from '../utils/testDataLoader';

const testCases = loadTestData('documents-positive.json');

for (const data of testCases) {
  test(`${data.testCaseId}: ${data.description}`, async ({ page }) => {
    const kaPage = new KnowledgeAssistantPage(page);
    await kaPage.open();

    if (data.testCaseId === 'UI-D-POS-008') {
      await kaPage.selectContext('Americas', 'Employee');
      await assertDocumentsPanel(kaPage, {
        ...data,
        expectedDocumentIds: ['D-001'],
      });

      await kaPage.selectContext('EMEA', 'Employee');
      const emeaIds = await kaPage.getVisibleDocumentIds();
      expect(emeaIds).toContain('D-002');
      expect(emeaIds).not.toContain('D-001');
      return;
    }

    await kaPage.selectContext(data.region!, data.role!);
    await assertDocumentsPanel(kaPage, data);
  });
}
