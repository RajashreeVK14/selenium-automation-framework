package com.ka.api.enums;

public enum UserRole {
    EMPLOYEE("Employee"),
    ENGINEERING("Engineering"),
    FINANCE("Finance"),
    MANAGER("Manager");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
