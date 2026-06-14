package com.ka.api.enums;

public enum DocumentState {
    DRAFT("Draft"),
    IN_REVIEW("In Review"),
    APPROVED("Approved"),
    RETIRED("Retired");

    private final String value;

    DocumentState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
