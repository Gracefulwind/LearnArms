package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.ScaleGestureDetectorApi27;
import com.gracefulwind.learnarms.reader.widget.TestFrameLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: TestGestureActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.TEST_GESTURE_ACTIVITY)
public class TestGestureActivity extends MyBaseActivity {
    public static final String TAG = "TestGestureActivity";

    @BindView(R2.id.ratg_btn_0)
    Button btn0;
    @BindView(R2.id.ratg_fl_test)
    TestFrameLayout ratgFlTest;
    @BindView(R2.id.ratg_et_test)
    TextView ratgEtTest;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.reader_activity_test_gesture;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
//        ratgFlTest.setScaleListener(new TestFrameLayout.OnScaleListener() {
//
//            @Override
//            public boolean onScaled(ScaleGestureDetectorApi27 detector) {
//                float scaleFactor = detector.getScaleFactor();
//                float currentSpan = detector.getCurrentSpan();
//                float currentSpanX = detector.getCurrentSpanX();
//                float currentSpanY = detector.getCurrentSpanY();
//                LogUtil.e(TAG, "scaleFactor = " + scaleFactor + " , currentSpan = " + currentSpan
//                    + " , currentSpanX = " + currentSpanX + " , currentSpanY = " + currentSpanY);
//                if(scaleFactor < 1.1 && scaleFactor > 0.9){
//                    return false;
//                }else {
//                    ratgEtTest.setScaleX(scaleFactor);
//                    ratgEtTest.setScaleY(scaleFactor);
//                    return true;
//                }
//            }
//        });
        ratgFlTest.setChild(ratgEtTest);
    }

    @OnClick({R2.id.ratg_btn_0,R2.id.ratg_btn_1,R2.id.ratg_btn_2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.ratg_btn_0 == id){
            LogUtil.e(TAG, "ratgFlTest = " + ratgFlTest.getHeight() + " , ratgEtTest = " + ratgEtTest.getHeight());
//            //不调用onDraw
//            ratgFlTest.requestLayout();
//            //只调用onDraw
//            ratgFlTest.invalidate();
        }else if(R.id.ratg_btn_1 == id){
            ratgEtTest.setEnabled(!ratgEtTest.isEnabled());
        }else if(R.id.ratg_btn_2 == id){
//            ratgEtTest.setEnabled(!ratgEtTest.isEnabled());

        }
    }

}
