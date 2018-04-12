package com.ecnu.common.enums;

public enum ResponseStatusEnum {
    SUCCESS("success"),

    FAIL("fail"),
    NO_USER_FAIL("noUserFail"),
    DUPLICATE_USERNAME_FAIL("duplicateUsernameFail"),
    AUTH_FAIL("authFail"),
    INPUT_FAIL("inputFail"),
    INVALID_INPUT_FAIL("invalidInputFail"),
    SQL_FAIL("sqlFail"),
    STEP_FAIL("stepFail");


    private String desc;

    ResponseStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
