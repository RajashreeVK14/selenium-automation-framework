import { Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class KnowledgeAssistantPage extends BasePage {
  readonly regionSelect: Locator;
  readonly roleSelect: Locator;
  readonly questionInput: Locator;
  readonly askButton: Locator;
  readonly answerArea: Locator;
  readonly citationsContainer: Locator;
  readonly documentsPanel: Locator;

  private static readonly PLACEHOLDER = 'Answers will appear here.';

  constructor(page: Page) {
    super(page);
    this.regionSelect = page.getByTestId('region');
    this.roleSelect = page.getByTestId('role');
    this.questionInput = page.getByTestId('question');
    this.askButton = page.getByTestId('ask');
    this.answerArea = page.getByTestId('answer');
    this.citationsContainer = page.getByTestId('cites');
    this.documentsPanel = page.getByTestId('docs');
  }

  async open() {
    await this.goto('/');
    await this.regionSelect.waitFor({ state: 'visible' });
  }

  async selectContext(region: string, role: string) {
    await this.regionSelect.selectOption(region);
    await this.roleSelect.selectOption(role);
    await this.page.waitForTimeout(300);
  }

  async submitQuestion(question: string) {
    await this.questionInput.fill(question);
    await this.askButton.click();
  }

  async askQuestion(question: string) {
    await this.submitQuestion(question);
    await this.page.waitForFunction(
      (placeholder) => {
        const el = document.querySelector('[data-testid="answer"]');
        return el && el.textContent !== placeholder && el.textContent !== 'Thinking...';
      },
      KnowledgeAssistantPage.PLACEHOLDER,
      { timeout: 15000 }
    );
  }

  async pressEnterToAsk(question: string) {
    await this.questionInput.fill(question);
    await this.questionInput.press('Enter');
    await this.page.waitForFunction(
      (placeholder) => {
        const el = document.querySelector('[data-testid="answer"]');
        return el && el.textContent !== placeholder && el.textContent !== 'Thinking...';
      },
      KnowledgeAssistantPage.PLACEHOLDER,
      { timeout: 15000 }
    );
  }

  async getAnswerText(): Promise<string> {
    return (await this.answerArea.textContent())?.trim() ?? '';
  }

  async getCitationTexts(): Promise<string[]> {
    return this.citationsContainer.getByTestId('citation').allTextContents();
  }

  async getVisibleDocumentIds(): Promise<string[]> {
    const docs = this.documentsPanel.getByTestId('doc');
    const count = await docs.count();
    const ids: string[] = [];
    for (let i = 0; i < count; i++) {
      const id = await docs.nth(i).getAttribute('data-doc-id');
      if (id) ids.push(id);
    }
    return ids;
  }

  async getVisibleDocumentCount(): Promise<number> {
    return this.documentsPanel.getByTestId('doc').count();
  }

  async isAnswerPlaceholder(): Promise<boolean> {
    const text = await this.getAnswerText();
    return text === KnowledgeAssistantPage.PLACEHOLDER || text === '';
  }

  async waitForDocumentsToLoad() {
    await this.documentsPanel.waitFor({ state: 'visible' });
    await this.page.waitForTimeout(500);
  }
}
