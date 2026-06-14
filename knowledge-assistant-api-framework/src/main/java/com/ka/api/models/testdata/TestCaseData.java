package com.ka.api.models.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseData {

    private String testCaseId;
    private String description;
    private String region;
    private String role;
    private String question;
    private String regionQuery;
    private String audienceQuery;
    private String stateQuery;
    private Integer expectedStatusCode;
    private String expectedSchema;
    private String expectedErrorContains;
    private Map<String, String> headers;
    private JsonNode requestBody;
    private String contentType;
    private boolean validateAnswerNotEmpty;
    private boolean validateCitationsPresent;
    private String expectedUserRegion;
    private String expectedUserRole;
    private Integer expectMinDocumentCount;
    private boolean expectEmptyArray;
    private String behaviorRule;
    private boolean expectRefusal;
    private List<String> forbiddenCitations;
    private List<String> allowedCitations;
    private List<String> answerMustContain;
    private List<String> answerMustNotContain;
    private boolean requireCitationsWhenAnswered;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRegionQuery() {
        return regionQuery;
    }

    public void setRegionQuery(String regionQuery) {
        this.regionQuery = regionQuery;
    }

    public String getAudienceQuery() {
        return audienceQuery;
    }

    public void setAudienceQuery(String audienceQuery) {
        this.audienceQuery = audienceQuery;
    }

    public String getStateQuery() {
        return stateQuery;
    }

    public void setStateQuery(String stateQuery) {
        this.stateQuery = stateQuery;
    }

    public Integer getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public void setExpectedStatusCode(Integer expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }

    public String getExpectedSchema() {
        return expectedSchema;
    }

    public void setExpectedSchema(String expectedSchema) {
        this.expectedSchema = expectedSchema;
    }

    public String getExpectedErrorContains() {
        return expectedErrorContains;
    }

    public void setExpectedErrorContains(String expectedErrorContains) {
        this.expectedErrorContains = expectedErrorContains;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public JsonNode getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(JsonNode requestBody) {
        this.requestBody = requestBody;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isValidateAnswerNotEmpty() {
        return validateAnswerNotEmpty;
    }

    public void setValidateAnswerNotEmpty(boolean validateAnswerNotEmpty) {
        this.validateAnswerNotEmpty = validateAnswerNotEmpty;
    }

    public boolean isValidateCitationsPresent() {
        return validateCitationsPresent;
    }

    public void setValidateCitationsPresent(boolean validateCitationsPresent) {
        this.validateCitationsPresent = validateCitationsPresent;
    }

    public String getExpectedUserRegion() {
        return expectedUserRegion;
    }

    public void setExpectedUserRegion(String expectedUserRegion) {
        this.expectedUserRegion = expectedUserRegion;
    }

    public String getExpectedUserRole() {
        return expectedUserRole;
    }

    public void setExpectedUserRole(String expectedUserRole) {
        this.expectedUserRole = expectedUserRole;
    }

    public Integer getExpectMinDocumentCount() {
        return expectMinDocumentCount;
    }

    public void setExpectMinDocumentCount(Integer expectMinDocumentCount) {
        this.expectMinDocumentCount = expectMinDocumentCount;
    }

    public boolean isExpectEmptyArray() {
        return expectEmptyArray;
    }

    public void setExpectEmptyArray(boolean expectEmptyArray) {
        this.expectEmptyArray = expectEmptyArray;
    }

    public String getBehaviorRule() {
        return behaviorRule;
    }

    public void setBehaviorRule(String behaviorRule) {
        this.behaviorRule = behaviorRule;
    }

    public boolean isExpectRefusal() {
        return expectRefusal;
    }

    public void setExpectRefusal(boolean expectRefusal) {
        this.expectRefusal = expectRefusal;
    }

    public List<String> getForbiddenCitations() {
        return forbiddenCitations;
    }

    public void setForbiddenCitations(List<String> forbiddenCitations) {
        this.forbiddenCitations = forbiddenCitations;
    }

    public List<String> getAllowedCitations() {
        return allowedCitations;
    }

    public void setAllowedCitations(List<String> allowedCitations) {
        this.allowedCitations = allowedCitations;
    }

    public List<String> getAnswerMustContain() {
        return answerMustContain;
    }

    public void setAnswerMustContain(List<String> answerMustContain) {
        this.answerMustContain = answerMustContain;
    }

    public List<String> getAnswerMustNotContain() {
        return answerMustNotContain;
    }

    public void setAnswerMustNotContain(List<String> answerMustNotContain) {
        this.answerMustNotContain = answerMustNotContain;
    }

    public boolean isRequireCitationsWhenAnswered() {
        return requireCitationsWhenAnswered;
    }

    public void setRequireCitationsWhenAnswered(boolean requireCitationsWhenAnswered) {
        this.requireCitationsWhenAnswered = requireCitationsWhenAnswered;
    }
}
