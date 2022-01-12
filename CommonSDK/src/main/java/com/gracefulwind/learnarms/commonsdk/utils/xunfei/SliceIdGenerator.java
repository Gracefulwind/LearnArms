package com.gracefulwind.learnarms.commonsdk.utils.xunfei;

/**
 * @ClassName: SliceIdGenerator
 * @Author: Gracefulwind
 * @CreateDate: 2022/1/12
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/1/12
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SliceIdGenerator {

    private static final String INIT_STR = "aaaaaaaaa`";
    private int length = 0;
    private char[] ch;

    public SliceIdGenerator() {
        this.length = INIT_STR.length();
        this.ch = INIT_STR.toCharArray();
    }

    /**
     * 获取sliceId
     *
     * @return
     */
    public String getNextSliceId() {
        for (int i = 0, j = length - 1; i < length && j >= 0; i++) {
            if (ch[j] != 'z') {
                ch[j]++;
                break;
            } else {
                ch[j] = 'a';
                j--;
                continue;
            }
        }

        return new String(ch);
    }

}
