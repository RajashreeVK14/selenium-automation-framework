package com.ka.api.tests;

import com.ka.api.base.BaseApiTest;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.ResponseValidator;
import com.ka.api.utils.TestDataFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DocumentsApiPositiveTest extends BaseApiTest {

    @Test(dataProvider = "jsonTestData")
    @TestDataFile("testdata/documents/documents_positive_tests.json")
    public void documents_positive_scenarios(TestCaseData data) {
        Response response = documentsApiClient.getDocuments(
                data.getRegion(),
                data.getRole(),
                data.getRegionQuery(),
                data.getAudienceQuery(),
                data.getStateQuery()
        );

        ResponseValidator.assertStatusCode(response, data.getExpectedStatusCode());

        if (data.getExpectedSchema() != null) {
            ResponseValidator.assertJsonSchema(response, data.getExpectedSchema());
        }

        ResponseValidator.assertDocumentsArray(response);

        if (data.getExpectMinDocumentCount() != null || data.isExpectEmptyArray()) {
            ResponseValidator.assertDocumentCount(
                    response,
                    data.getExpectMinDocumentCount(),
                    data.isExpectEmptyArray()
            );
        }
    }
}
