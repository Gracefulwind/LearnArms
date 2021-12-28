package com.gracefulwind.learnarms.commonsdk.utils.audio;

/**
 * @ClassName: RecordStreamListener
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public interface RecordStreamListener {
    void recordOfByte(byte[] data, int begin, int end);
}
