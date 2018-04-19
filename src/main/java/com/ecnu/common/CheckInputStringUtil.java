package com.ecnu.common;

/**
 * 检查前端的输入框是否有各种非法字符
 * @author asus
 */
public class CheckInputStringUtil {
    public static Boolean containIllegalCharacter(String str) {
        String beLessChar = "<";
        String beMoreChar = ">";
        if (str.contains(beLessChar) || str.contains(beMoreChar)) {
            return true;
        } else {
            return false;
        }
    }
}
