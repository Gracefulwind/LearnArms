package com.gracefulwind.learnarms.commonsdk.utils;

public class StringUtil {
    /**
     * 判断是否为空字符串最优代码
     * @param str
     * @return 如果为空，则返回true
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     * @param str 如果不为空，则返回true
     * @return
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否相等
     * @param str1 比较的第一个字符
     * @param str1 比较的第二个字符
     * @return
     */
    public static  boolean equals(String str1, String str2){
        return (str1 == str2) || (str1 != null && str1.equals(str2));
    }
}
