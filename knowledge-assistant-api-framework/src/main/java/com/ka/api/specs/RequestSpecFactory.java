package com.ka.api.specs;

import com.ka.api.config.ApiConfig;
import com.ka.api.constants.ApiHeaders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private static RequestSpecification baseSpec;

    private RequestSpecFactory() {
    }

    public static RequestSpecification getBaseSpec() {
        if (baseSpec == null) {
            baseSpec = new RequestSpecBuilder()
                    .setBaseUri(ApiConfig.getBaseUri())
                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();
        }
        return baseSpec;
    }

    public static RequestSpecification withAuthHeaders(String region, String role) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpec())
                .addHeader(ApiHeaders.USER_REGION, region)
                .addHeader(ApiHeaders.USER_ROLE, role)
                .build();
    }
}
