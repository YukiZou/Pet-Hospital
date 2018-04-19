package com.ecnu.common.enums;


/**
 * 表明用户权限的枚举类
 */
public enum  UserAuthEnum {
    ORDINARY_USER(1, "ordinary user"),
    ADMIN(2, "admin"),
    SUPER_ADMIN(3, "super admin");
    private int value;
    private String desc;

    UserAuthEnum(int value, String desc) {
        this.value = value;
        this.desc =desc;
    }

    public static String getDesc(int value) {
        for (UserAuthEnum u: UserAuthEnum.values()) {
            if (u.value == value) {
                return u.desc;
            }
        }
        return null;
    }

    public static UserAuthEnum getUserAuthEnum(int value) {
        for (UserAuthEnum u: UserAuthEnum.values()) {
            if (u.value == value) {
                return u;
            }
        }
        return null;
    }

    public static boolean isOrdinaryUser(UserAuthEnum  userAuthEnum) {
        if (userAuthEnum.value == 1) {
            return true;
        } else {
            return false;
        }
    }

}
