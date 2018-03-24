package com.ecnu.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 */

public class MD5Util {
    public static String toMd5(String str) {
        StringBuffer md5Code = new StringBuffer();
        try {
            //获取加密方式为md5的算法对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //使用指定的 byte 数组更新messageDigest
            messageDigest.update(str.getBytes());
            //完成hash计算，同时调用digest方法之后messageDigest对象被重置
            byte[] digest =  messageDigest.digest();
            md5Code = toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Code.toString();
    }


    /**
     * 将16位byte[] 转换为32位StringBuffer
     * @param buffer
     * @return
     */
    private static StringBuffer toHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }
        return sb;
    }
}
