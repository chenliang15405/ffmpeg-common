package com.ffmpeg.common.utils;

/**
 * @auther alan.chen
 * @time 2019/10/8 2:14 PM
 */
public class StrUtils {


    public static boolean checkEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean checkNotEmpty(CharSequence cs) {
        return !checkEmpty(cs);
    }

    public static boolean checkBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean checkNotBlank(CharSequence cs) {
        return !checkBlank(cs);
    }
}
