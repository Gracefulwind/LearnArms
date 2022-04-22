package com.gracefulwind.learnarms.reader.utils;

/**
 * @ClassName: Charset
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/22
 * @Description: ---------------------------
 * 编码类型
 * @UpdateUser:
 * @UpdateDate: 2022/4/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public enum Charset {
    UTF8("UTF-8"),
    UTF16LE("UTF-16LE"),
    UTF16BE("UTF-16BE"),
    GBK("GBK");

    private String mName;
    public static final byte BLANK = 0x0a;

    private Charset(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}