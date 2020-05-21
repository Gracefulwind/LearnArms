package com.gracefulwind.learnarms.module_test_dagger.mvp.model.entity;

import com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity.DaggerPage2Activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * @ClassName: DaggerPage1Item1
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/14 17:38
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/14 17:38
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class DaggerPage1Item1 {

    String itemFrom = "null";
    String time = "null";

    public DaggerPage1Item1() {

    }

    @Inject
    public DaggerPage1Item1(DaggerPage2Activity activity) {
        this.itemFrom = activity.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        this.time = dateString;
    }

    public DaggerPage1Item1(String itemFrom, String time) {
        this.itemFrom = itemFrom;
        this.time = time;
    }

    @Override
    public String toString() {
        return "DaggerPage1Item1{" +
                "itemFrom='" + itemFrom + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
