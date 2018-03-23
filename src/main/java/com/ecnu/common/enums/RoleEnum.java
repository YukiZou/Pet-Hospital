package com.ecnu.common.enums;


public enum RoleEnum {
    RECEPTIONIST(1, "前台"),
    ASSISTANT_DOCTOR(2, "医助"),
    VETERINARIAN(3, "兽医");
    private int value;
    private String decs;

    RoleEnum(int value, String desc) {
        this.value = value;
        this.decs = desc;
    }
}
