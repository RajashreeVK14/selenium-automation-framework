import * as fs from 'fs';
import * as path from 'path';

export interface UiTestCase {
  testCaseId: string;
  description: string;
  region?: string;
  role?: string;
  question?: string;
  expectedAnswerContains?: string[];
  expectedAnswerNotContains?: string[];
  expectedCitations?: string[];
  forbiddenCitations?: string[];
  expectedMinCitations?: number;
  expectRefusal?: boolean;
  expectPlaceholderAnswer?: boolean;
  expectedDocumentIds?: string[];
  forbiddenDocumentIds?: string[];
  expectedMinDocuments?: number;
  expectedMaxDocuments?: number;
  useEnterKey?: boolean;
}

export function loadTestData(fileName: string): UiTestCase[] {
  const filePath = path.join(__dirname, '..', 'test-data', fileName);
  const raw = fs.readFileSync(filePath, 'utf-8');
  return JSON.parse(raw) as UiTestCase[];
}
