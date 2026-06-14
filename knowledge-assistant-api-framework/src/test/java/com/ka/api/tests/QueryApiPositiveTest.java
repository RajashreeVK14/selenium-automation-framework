package com.ka.api.tests;

import com.ka.api.base.BaseApiTest;
import com.ka.api.models.request.QueryRequest;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.ResponseValidator;
import com.ka.api.utils.TestDataFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class QueryApiPositiveTest extends BaseApiTest {

    @Test(dataProvider = "jsonTestData")
    @TestDataFile("testdata/query/query_positive_tests.json")
    public void query_positive_scenarios(TestCaseData data) {
        QueryRequest request = new QueryRequest(data.getQuestion());

        Response response = queryApiClient.postQuery(data.getRegion(), data.getRole(), request);

        ResponseValidator.assertStatusCode(response, data.getExpectedStatusCode());

        if (data.getExpectedSchema() != null) {
            ResponseValidator.assertJsonSchema(response, data.getExpectedSchema());
        }

        ResponseValidator.assertQueryResponse(
                response,
                data.isValidateAnswerNotEmpty(),
                data.isValidateCitationsPresent()
        );
    }
}
