package com.gracefulwind.learnarms.commonsdk.utils;

import android.util.Log;

public class LogUtil {

    /**
     * 截断输出日志
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 2 * 1024;
        long length = msg.length();
        for(int x = 0; x * segmentSize <= length; x++){
            int start = x * segmentSize;
            int end = (x+1) * segmentSize;
            if(end >= length){
                end = (int) length;
            }
            String tempMsg = msg.substring(start, end);
            Log.v(tag,"======  "+ tempMsg);
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 2 * 1024;
        long length = msg.length();
        for(int x = 0; x * segmentSize <= length; x++){
            int start = x * segmentSize;
            int end = (x+1) * segmentSize;
            if(end >= length){
                end = (int) length;
            }
            String tempMsg = msg.substring(start, end);
            Log.d(tag,"======  "+ tempMsg);
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 2 * 1024;
        long length = msg.length();
        for(int x = 0; x * segmentSize <= length; x++){
            int start = x * segmentSize;
            int end = (x+1) * segmentSize;
            if(end >= length){
                end = (int) length;
            }
            String tempMsg = msg.substring(start, end);
            Log.i(tag,"======  "+ tempMsg);
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 2 * 1024;
        long length = msg.length();
        for(int x = 0; x * segmentSize <= length; x++){
            int start = x * segmentSize;
            int end = (x+1) * segmentSize;
            if(end >= length){
                end = (int) length;
            }
            String tempMsg = msg.substring(start, end);
            Log.w(tag,"======  "+ tempMsg);
        }
    }

    /**
     * 截断输出日志
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 2 * 1024;
        long length = msg.length();
        for(int x = 0; x * segmentSize <= length; x++){
            int start = x * segmentSize;
            int end = (x+1) * segmentSize;
            if(end >= length){
                end = (int) length;
            }
            String tempMsg = msg.substring(start, end);
            Log.e(tag,"======  "+ tempMsg);
        }
    }

}
