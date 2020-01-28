package com.BookKeeping.util;

public class StringUtil {

    /**
     * 在字符串前后加%
     */
    public static String formatLike(String str) {
        if(isNotEmpty(str)) {
            return "%" + str + "%";
        }
        return null;
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(String str) {
        if(str != null && !"".equals(str.trim())) {
            return true;
        }
        return false;
    }

}
