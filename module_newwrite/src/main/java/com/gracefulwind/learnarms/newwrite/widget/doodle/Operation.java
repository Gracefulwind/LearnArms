package com.gracefulwind.learnarms.newwrite.widget.doodle;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Operation
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/9
 * @Description: ---------------------------
 * 记录所有涂鸦画板层操作的类
 * @UpdateUser:
 * @UpdateDate: 2021/9/9
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class Operation {
//== static ===========================
    public static final String TAG = Operation.class.getName();
    /**
     * 操作类，用于拓展
     * 可以删了。。。
     * */
    public static final int DOODLE = 0x00000000;
    public static final int CORP = 0x00000001;
    public static final int ERASER = 0x00000002;


//== operate ============================
////这几个存在paint里就可以了，不用再单独存
//    //操作类型，暂时就doodle
//    int type = DOODLE;
//    int paintColor;
//    int paintWidth = 14;

    Paint paint;
    Path path;
    int operationType = DOODLE;
    boolean isFinished = false;
    /**
     * 轨迹行为存储
     * */
    List<PointInfo> pointInfos = new ArrayList<>();

//    public Operation() {
//
//    }

    public Operation(Path path, Paint paint) {
        this.paint = paint;
        this.path = path;
    }

    public void moveTo(float x, float y) {
        path.moveTo(x, y);
//        PointInfo pointInfo = new PointInfo(x, y, PointInfo.Way.Move);
        pointInfos.add(new PointInfo(x, y, PointInfo.Way.Move));
    }

    public void quadTo(float x1, float y1, float x2, float y2) {
        path.quadTo(x1, y1, x2, y2);
        pointInfos.add(new PointInfo(x1, y1, x2, y2, PointInfo.Way.QuadTo));
    }



//===================================================================================================
    /**
     * 点信息
     * */
    public static class PointInfo{
        float x1;
        float y1;
        float x2;
        float y2;
        Way way;

        public PointInfo() {
        }

        public PointInfo(Way way) {
            this.way = way;
        }

        public PointInfo(float x, float y, Way way) {
            this.x1 = x;
            this.y1 = y;
            this.way = way;
        }
        public PointInfo(float x1, float y1, float x2, float y2, Way way) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.way = way;
        }

        /**
         * 操作行为
         * */
        public enum Way{
            Move,
            QuadTo;
        }
        
    }

}
