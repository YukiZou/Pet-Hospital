package com.ecnu.common.enums;

public enum ResponseStatusEnum {
    SUCCESS("success"), SQL_FAIL("sqlFail"), AUTH_FAIL("authFail"), INPUT_FAIL("inputFail"), FAIL("fail");

    private String desc;

    ResponseStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
