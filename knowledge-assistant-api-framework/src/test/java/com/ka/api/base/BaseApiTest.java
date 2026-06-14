package com.ka.api.base;

import com.ka.api.clients.DocumentsApiClient;
import com.ka.api.clients.QueryApiClient;
import com.ka.api.config.ApiConfig;
import com.ka.api.models.testdata.TestCaseData;
import com.ka.api.utils.TestDataFile;
import com.ka.api.utils.TestDataLoader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public abstract class BaseApiTest {

    protected QueryApiClient queryApiClient;
    protected DocumentsApiClient documentsApiClient;

    @BeforeClass(alwaysRun = true)
    public void setUpApi() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = ApiConfig.getBaseUri();
        queryApiClient = new QueryApiClient();
        documentsApiClient = new DocumentsApiClient();
    }

    @DataProvider(name = "jsonTestData")
    public static Iterator<Object[]> jsonTestData(Method method) {
        TestDataFile annotation = method.getAnnotation(TestDataFile.class);
        if (annotation == null) {
            throw new IllegalStateException("@TestDataFile is required on test method: " + method.getName());
        }

        List<TestCaseData> testCases = TestDataLoader.loadTestCases(annotation.value());
        return testCases.stream()
                .map(testCase -> new Object[]{testCase})
                .iterator();
    }
}
