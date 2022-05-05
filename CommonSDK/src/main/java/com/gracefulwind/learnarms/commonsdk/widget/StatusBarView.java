package com.gracefulwind.learnarms.commonsdk.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: StatusBarView
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/4/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class StatusBarView extends FrameLayout {
    public StatusBarView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public StatusBarView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
