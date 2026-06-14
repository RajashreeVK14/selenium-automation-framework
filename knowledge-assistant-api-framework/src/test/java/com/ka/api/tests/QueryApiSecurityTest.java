package com.ka.api.tests;

import com.ka.api.base.BaseApiTest;
import com.ka.api.models.request.QueryRequest;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.ResponseValidator;
import com.ka.api.utils.TestDataFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Validates region/role-scoped access described in the OpenAPI spec.
 */
public class QueryApiSecurityTest extends BaseApiTest {

    @Test(dataProvider = "jsonTestData")
    @TestDataFile("testdata/query/query_security_matrix.json")
    public void query_security_matrix(TestCaseData data) {
        Response response = queryApiClient.postQuery(
                data.getRegion(),
                data.getRole(),
                new QueryRequest(data.getQuestion())
        );

        ResponseValidator.assertStatusCode(response, data.getExpectedStatusCode());
        ResponseValidator.assertJsonSchema(response, data.getExpectedSchema());
        ResponseValidator.assertQueryResponse(
                response,
                data.isValidateAnswerNotEmpty(),
                data.isValidateCitationsPresent()
        );
    }
}
