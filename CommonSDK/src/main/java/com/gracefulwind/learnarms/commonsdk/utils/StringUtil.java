package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;

import com.zqc.opencc.android.lib.ChineseConverter;
import com.zqc.opencc.android.lib.ConversionType;

public class StringUtil {

    //todo:read 后续修改文件层级
    public static final String SHARED_READ_CONVERT_TYPE = "shared_read_convert_type";

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

    /**
     * 将文本中的半角字符，转换成全角字符
     * @param input
     * @return
     */
    public static String halfToFull(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++)
        {
            if (c[i] == 32) //半角空格
            {
                c[i] = (char) 12288;
                continue;
            }
            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i]> 32 && c[i]< 127)    //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    //功能：字符串全角转换为半角
    public static String fullToHalf(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++)
        {
            if (c[i] == 12288) //全角空格
            {
                c[i] = (char) 32;
                continue;
            }

            if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    //繁簡轉換
    public static String convertCC(String input, Context context)
    {
        ConversionType currentConversionType = ConversionType.S2TWP;
        int convertType = SharedPreUtils.getInstance().getInt(SHARED_READ_CONVERT_TYPE, 0);

        if (input.length() == 0)
            return "";

        switch (convertType) {
            case 1:
                currentConversionType = ConversionType.TW2SP;
                break;
            case 2:
                currentConversionType = ConversionType.S2HK;
                break;
            case 3:
                currentConversionType = ConversionType.S2T;
                break;
            case 4:
                currentConversionType = ConversionType.S2TW;
                break;
            case 5:
                currentConversionType = ConversionType.S2TWP;
                break;
            case 6:
                currentConversionType = ConversionType.T2HK;
                break;
            case 7:
                currentConversionType = ConversionType.T2S;
                break;
            case 8:
                currentConversionType = ConversionType.T2TW;
                break;
            case 9:
                currentConversionType = ConversionType.TW2S;
                break;
            case 10:
                currentConversionType = ConversionType.HK2S;
                break;
        }

        return (convertType != 0) ? ChineseConverter.convert(input, currentConversionType, context) : input;
    }
}
