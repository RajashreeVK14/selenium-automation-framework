package com.ka.api.tests;

import com.ka.api.base.BaseApiTest;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.ResponseValidator;
import com.ka.api.utils.TestDataFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DocumentsApiNegativeTest extends BaseApiTest {

    @Test(dataProvider = "jsonTestData")
    @TestDataFile("testdata/documents/documents_negative_tests.json")
    public void documents_negative_scenarios(TestCaseData data) {
        Response response;

        if (data.getHeaders() != null) {
            Map<String, String> queryParams = new HashMap<>();
            if (data.getStateQuery() != null) {
                queryParams.put("state", data.getStateQuery());
            }
            response = documentsApiClient.getDocumentsWithHeaders(data.getHeaders(), queryParams);
        } else {
            response = documentsApiClient.getDocuments(
                    data.getRegion(),
                    data.getRole(),
                    data.getRegionQuery(),
                    data.getAudienceQuery(),
                    data.getStateQuery()
            );
        }

        ResponseValidator.assertStatusCode(response, data.getExpectedStatusCode());
        ResponseValidator.assertDocumentsArray(response);
        ResponseValidator.assertDocumentCount(
                response,
                data.getExpectMinDocumentCount(),
                data.isExpectEmptyArray()
        );
    }
}
