package com.ecnu.common;

/**
 * 检查前端的输入框是否有各种非法字符
 */
public class CheckInputStringUtil {
    public static Boolean containIllegalCharacter(String str) {
        if (str.contains("<") || str.contains(">")) {
            return true;
        } else {
            return false;
        }
    }
}
