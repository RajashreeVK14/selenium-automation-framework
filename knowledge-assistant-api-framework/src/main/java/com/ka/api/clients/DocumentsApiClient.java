package com.ka.api.clients;

import com.ka.api.constants.ApiEndpoints;
import com.ka.api.constants.ApiHeaders;
import com.ka.api.specs.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DocumentsApiClient {

    public Response getDocuments(String region, String role) {
        return given()
                .spec(RequestSpecFactory.withAuthHeaders(region, role))
                .when()
                .get(ApiEndpoints.DOCUMENTS);
    }

    public Response getDocuments(String region, String role, String regionQuery, String audienceQuery, String stateQuery) {
        Map<String, String> queryParams = new HashMap<>();
        if (regionQuery != null) {
            queryParams.put("region", regionQuery);
        }
        if (audienceQuery != null) {
            queryParams.put("audience", audienceQuery);
        }
        if (stateQuery != null) {
            queryParams.put("state", stateQuery);
        }

        return given()
                .spec(RequestSpecFactory.withAuthHeaders(region, role))
                .queryParams(queryParams)
                .when()
                .get(ApiEndpoints.DOCUMENTS);
    }

    public Response getDocumentsWithHeaders(Map<String, String> headers, Map<String, String> queryParams) {
        RequestSpecification spec = given().spec(RequestSpecFactory.getBaseSpec());

        if (headers != null) {
            headers.forEach(spec::header);
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            spec.queryParams(queryParams);
        }

        return spec.when().get(ApiEndpoints.DOCUMENTS);
    }

    public Response getDocumentsWithoutHeader(String missingHeader, String region, String role) {
        RequestSpecification spec = given().spec(RequestSpecFactory.getBaseSpec());

        if (!ApiHeaders.USER_REGION.equals(missingHeader) && region != null) {
            spec.header(ApiHeaders.USER_REGION, region);
        }
        if (!ApiHeaders.USER_ROLE.equals(missingHeader) && role != null) {
            spec.header(ApiHeaders.USER_ROLE, role);
        }

        return spec.when().get(ApiEndpoints.DOCUMENTS);
    }
}
