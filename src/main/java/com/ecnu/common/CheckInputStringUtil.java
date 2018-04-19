package com.ecnu.common;

/**
 * 检查前端的输入框是否有各种非法字符
 */
public class CheckInputStringUtil {
    public static Boolean containIllegalCharacter(String str) {
        String beLessChar = "<";
        String beMoreChar = ">";
        String slash = "/";
        String questionMark = "?";
        if (str.contains(beLessChar) || str.contains(beMoreChar) || str.contains(slash) || str.contains(questionMark) ) {
            return true;
        } else {
            return false;
        }
    }
}
