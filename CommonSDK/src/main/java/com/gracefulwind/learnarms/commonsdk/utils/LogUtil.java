package com.gracefulwind.learnarms.commonsdk.utils;

import android.os.DeadSystemException;
import android.util.Log;

import java.net.UnknownHostException;

public class LogUtil {

    /**
     * 截断输出日志
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0){
            return;
        }

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
                || msg == null || msg.length() == 0){
            return;
        }

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
                || msg == null || msg.length() == 0){
            return;
        }

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
                || msg == null || msg.length() == 0){
            return;
        }

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
                || msg == null || msg.length() == 0){
            return;
        }

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

    /**
     * 截断输出日志
     * @param msg
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0){
            return;
        }

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
        if (tr != null) {
//            // This is to reduce the amount of log spew that apps do in the non-error
//            // condition of the network being unavailable.
//            Throwable t = tr;
//            while (t != null) {
//                if (t instanceof UnknownHostException) {
//                    break;
//                }
//                if (t instanceof DeadSystemException) {
//                    lbbw.println("DeadSystemException: The system died; "
//                            + "earlier logs will point to the root cause");
//                    break;
//                }
//                t = t.getCause();
//            }
//            if (t == null) {
//                tr.printStackTrace(lbbw);
//            }
            tr.printStackTrace();
        }
    }

}
