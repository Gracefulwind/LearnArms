package com.gracefulwind.learnarms.commonsdk.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

/**
  *
  * @ClassName:      ShapeUtil
  * @Author:         Gracefulwind
  * @CreateDate:     2020/4/15 14:35
  * @Description:
  *
  * @UpdateUser:
  * @UpdateDate:     2020/4/15 14:35
  * @UpdateRemark:
  * @Version:        1.0
  * @Email 429344332@qq.com
 */

public class ShapeUtil {

    /**
     * 创建一个Shape - GradientDrawable
     *
     * @param strokeWidth - 沿边线厚度；无则传0 ,dp
     * @param roundRadius - 圆角半径；无则传0 , dp
     * @param shape       - shape绘制类型(rectangle、oval等)；default = RECTANGLE = 0;OVAL = 1;OVAL = 1;LINE = 2;RING = 3;
     * @param strokeColor - 沿边线颜色；无则传null
     * @param fillColor   - 内部填充颜色
     * @return
     */
    public static GradientDrawable createShape(int strokeWidth,
                                               int roundRadius, int shape,
                                               String strokeColor, String fillColor) {

//        int targetStrokeWidth = strokeWidth; // px not dp
//        int targetRoundRadius = roundRadius; // px not dp
        int targetStrokeWidth = (int) UiUtil.dip2px(strokeWidth);
        int targetRoundRadius = (int) UiUtil.dip2px(roundRadius);


        GradientDrawable gd = new GradientDrawable();

        if (0 != shape || 1 != shape || 2 != shape || 3 != shape) {
            gd.setShape(GradientDrawable.RECTANGLE);
        } else {
            gd.setShape(shape);
        }
//        传0即可
//        if (-1 != targetRoundRadius) {
//            gd.setCornerRadius(targetRoundRadius);
//        }
        gd.setCornerRadius(targetRoundRadius);
        int targetStrokeColor = -1;
        try{
            if (!TextUtils.isEmpty(strokeColor)) {
                targetStrokeColor = Color.parseColor(strokeColor);
            }
        }catch (Exception e){

        }
        if (-1 != targetStrokeColor) {
            gd.setStroke(targetStrokeWidth, targetStrokeColor);
        }
        try{
            if(!TextUtils.isEmpty(strokeColor)){
                int targetFillColor = Color.parseColor(fillColor);
                gd.setColor(targetFillColor);
            }
        }catch (Exception e){
            //颜色错误，不上色
        }
        return gd;
    }

    public static GradientDrawable createCardShape(String strokeColor){
        return createShape(2, 10, GradientDrawable.RECTANGLE, strokeColor, "#00000000");
    }

}
