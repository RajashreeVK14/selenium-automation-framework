package com.ka.api.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ka.api.models.testdata.TestCaseData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class TestDataLoader {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private TestDataLoader() {
    }

    public static List<TestCaseData> loadTestCases(String resourcePath) {
        try (InputStream input = TestDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IllegalArgumentException("Test data file not found: " + resourcePath);
            }
            return MAPPER.readValue(input, new TypeReference<List<TestCaseData>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data from: " + resourcePath, e);
        }
    }
}
