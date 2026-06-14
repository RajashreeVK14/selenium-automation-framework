package com.ka.api.utils;

import com.ka.api.models.response.QueryResponse;
import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public final class ResponseValidator {

    private ResponseValidator() {
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertThat(response.getStatusCode(), equalTo(expectedStatusCode));
    }

    public static void assertJsonSchema(Response response, String schemaClasspath) {
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaClasspath));
    }

    public static void assertQueryResponse(Response response, boolean validateAnswerNotEmpty, boolean validateCitationsPresent) {
        QueryResponse queryResponse = response.as(QueryResponse.class);

        assertThat("answer field should be present", queryResponse.getAnswer(), notNullValue());
        if (validateAnswerNotEmpty) {
            assertThat("answer should not be blank", queryResponse.getAnswer().trim(), not(isEmptyString()));
        }
        if (validateCitationsPresent) {
            assertThat("citations field should be present", queryResponse.getCitations(), notNullValue());
        }
    }

    public static void assertErrorContains(Response response, String expectedErrorContains) {
        if (expectedErrorContains == null || expectedErrorContains.isBlank()) {
            return;
        }
        String body = response.getBody().asString();
        assertThat("Response body should contain expected error text",
                body.toLowerCase(), containsString(expectedErrorContains.toLowerCase()));
    }

    public static void assertDocumentsArray(Response response) {
        assertThat(response.jsonPath().getList("$"), notNullValue());
    }

    public static void assertUserContext(Response response, String expectedRegion, String expectedRole) {
        if (expectedRegion != null) {
            assertThat(response.jsonPath().getString("user.region"), equalTo(expectedRegion));
        }
        if (expectedRole != null) {
            assertThat(response.jsonPath().getString("user.role"), equalTo(expectedRole));
        }
    }

    public static void assertDocumentCount(Response response, Integer minCount, boolean expectEmpty) {
        int count = response.jsonPath().getList("$").size();
        if (expectEmpty) {
            assertThat(count, equalTo(0));
        } else if (minCount != null) {
            assertThat(count, greaterThanOrEqualTo(minCount));
        }
    }
}
