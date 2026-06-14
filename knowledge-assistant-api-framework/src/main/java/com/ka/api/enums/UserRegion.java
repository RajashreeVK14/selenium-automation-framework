package com.ka.api.enums;

public enum UserRegion {
    AMERICAS("Americas"),
    EMEA("EMEA"),
    APAC("APAC");

    private final String value;

    UserRegion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
