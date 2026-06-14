import { expect } from '@playwright/test';
import { KnowledgeAssistantPage } from '../pages/KnowledgeAssistantPage';
import { UiTestCase } from './testDataLoader';

const REFUSAL_PHRASE = 'do not have';

export async function assertQueryResponse(page: KnowledgeAssistantPage, data: UiTestCase) {
  const answer = await page.getAnswerText();
  const citations = await page.getCitationTexts();

  if (data.expectPlaceholderAnswer) {
    expect(await page.isAnswerPlaceholder()).toBeTruthy();
    return;
  }

  expect(answer.length).toBeGreaterThan(0);
  expect(answer).not.toBe('Thinking...');

  if (data.expectRefusal) {
    expect(answer.toLowerCase()).toContain(REFUSAL_PHRASE);
    expect(citations.length).toBe(0);
  }

  if (data.expectedAnswerContains) {
    for (const phrase of data.expectedAnswerContains) {
      expect(answer.toLowerCase()).toContain(phrase.toLowerCase());
    }
  }

  if (data.expectedAnswerNotContains) {
    for (const phrase of data.expectedAnswerNotContains) {
      expect(answer.toLowerCase()).not.toContain(phrase.toLowerCase());
    }
  }

  if (data.expectedMinCitations !== undefined) {
    expect(citations.length).toBeGreaterThanOrEqual(data.expectedMinCitations);
  }

  if (data.expectedCitations) {
    for (const cite of data.expectedCitations) {
      expect(citations.some((c) => c.includes(cite))).toBeTruthy();
    }
  }

  if (data.forbiddenCitations) {
    for (const cite of data.forbiddenCitations) {
      expect(citations.some((c) => c.includes(cite))).toBeFalsy();
    }
  }
}

export async function assertDocumentsPanel(page: KnowledgeAssistantPage, data: UiTestCase) {
  await page.waitForDocumentsToLoad();
  const docIds = await page.getVisibleDocumentIds();
  const count = docIds.length;

  if (data.expectedMinDocuments !== undefined) {
    expect(count).toBeGreaterThanOrEqual(data.expectedMinDocuments);
  }
  if (data.expectedMaxDocuments !== undefined) {
    expect(count).toBeLessThanOrEqual(data.expectedMaxDocuments);
  }
  if (data.expectedDocumentIds) {
    for (const id of data.expectedDocumentIds) {
      expect(docIds).toContain(id);
    }
  }
  if (data.forbiddenDocumentIds) {
    for (const id of data.forbiddenDocumentIds) {
      expect(docIds).not.toContain(id);
    }
  }
}
