package com.ecnu.common.enums;


public enum RoleEnum {
    RECEPTIONIST(1, "前台"),
    ASSISTANT_DOCTOR(2, "医助"),
    VETERINARIAN(3, "兽医");
    private int value;
    private String desc;

    RoleEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static RoleEnum getRoleEnum(int value) {
        for (RoleEnum r : RoleEnum.values()) {
            if (r.value == value) {
                return r;
            }
        }
        return null;
    }
    
}
