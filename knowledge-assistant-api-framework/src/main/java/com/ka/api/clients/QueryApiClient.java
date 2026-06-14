package com.ka.api.clients;

import com.ka.api.constants.ApiEndpoints;
import com.ka.api.constants.ApiHeaders;
import com.ka.api.models.request.QueryRequest;
import com.ka.api.specs.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class QueryApiClient {

    public Response postQuery(String region, String role, QueryRequest request) {
        return given()
                .spec(RequestSpecFactory.withAuthHeaders(region, role))
                .body(request)
                .when()
                .post(ApiEndpoints.QUERY);
    }

    public Response postQueryWithHeaders(Map<String, String> headers, Object body, String contentType) {
        RequestSpecification spec = given().spec(RequestSpecFactory.getBaseSpec());

        if (headers != null) {
            headers.forEach(spec::header);
        }
        if (contentType != null) {
            spec.contentType(contentType);
        }
        if (body != null) {
            if (contentType != null && contentType.toLowerCase().contains("text/plain")) {
                spec.body(resolvePlainTextBody(body));
            } else {
                spec.body(body);
            }
        }

        return spec.when().post(ApiEndpoints.QUERY);
    }

    private String resolvePlainTextBody(Object body) {
        if (body instanceof QueryRequest queryRequest) {
            return queryRequest.getQuestion();
        }
        return String.valueOf(body);
    }

    public Response postQueryRaw(String region, String role, String rawBody) {
        return given()
                .spec(RequestSpecFactory.withAuthHeaders(region, role))
                .body(rawBody)
                .when()
                .post(ApiEndpoints.QUERY);
    }

    public Response postQueryWithoutHeader(String missingHeader, String region, String role, QueryRequest request) {
        RequestSpecification spec = given().spec(RequestSpecFactory.getBaseSpec()).body(request);

        if (!ApiHeaders.USER_REGION.equals(missingHeader) && region != null) {
            spec.header(ApiHeaders.USER_REGION, region);
        }
        if (!ApiHeaders.USER_ROLE.equals(missingHeader) && role != null) {
            spec.header(ApiHeaders.USER_ROLE, role);
        }

        return spec.when().post(ApiEndpoints.QUERY);
    }
}
