package com.ka.api.tests;

import com.ka.api.base.BaseApiTest;
import com.ka.api.models.request.QueryRequest;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.ResponseValidator;
import com.ka.api.utils.TestDataFile;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class QueryApiNegativeTest extends BaseApiTest {

    @Test(dataProvider = "jsonTestData")
    @TestDataFile("testdata/query/query_negative_tests.json")
    public void query_negative_scenarios(TestCaseData data) {
        Response response = executeNegativeQuery(data);

        ResponseValidator.assertStatusCode(response, data.getExpectedStatusCode());
        ResponseValidator.assertUserContext(response, data.getExpectedUserRegion(), data.getExpectedUserRole());

        if (data.isValidateAnswerNotEmpty()) {
            ResponseValidator.assertQueryResponse(response, true, false);
        }
    }

    private Response executeNegativeQuery(TestCaseData data) {
        if ("Q-NEG-010".equals(data.getTestCaseId())) {
            return queryApiClient.postQueryRaw(
                    data.getRegion(),
                    data.getRole(),
                    "{ invalid json }"
            );
        }

        if (data.getHeaders() != null) {
            Object body = buildRequestBody(data);
            return queryApiClient.postQueryWithHeaders(
                    data.getHeaders(),
                    body,
                    data.getContentType() != null ? data.getContentType() : "application/json"
            );
        }

        if (data.getRequestBody() != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("X-User-Region", data.getRegion());
            headers.put("X-User-Role", data.getRole());
            return queryApiClient.postQueryWithHeaders(headers, data.getRequestBody(), "application/json");
        }

        if (data.getRequestBody() == null && data.getQuestion() == null && data.getRegion() != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("X-User-Region", data.getRegion());
            headers.put("X-User-Role", data.getRole());
            return queryApiClient.postQueryWithHeaders(headers, null, "application/json");
        }

        QueryRequest request = new QueryRequest(data.getQuestion());
        return queryApiClient.postQueryWithHeaders(
                buildDefaultHeaders(data),
                request,
                data.getContentType() != null ? data.getContentType() : "application/json"
        );
    }

    private Object buildRequestBody(TestCaseData data) {
        if (data.getRequestBody() != null) {
            return data.getRequestBody();
        }
        if (data.getQuestion() != null) {
            return new QueryRequest(data.getQuestion());
        }
        return null;
    }

    private Map<String, String> buildDefaultHeaders(TestCaseData data) {
        Map<String, String> headers = new HashMap<>();
        if (data.getRegion() != null) {
            headers.put("X-User-Region", data.getRegion());
        }
        if (data.getRole() != null) {
            headers.put("X-User-Role", data.getRole());
        }
        return headers;
    }
}
