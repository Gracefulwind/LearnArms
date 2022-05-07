package com.gracefulwind.learnarms.commonsdk.interfaces;

import android.view.View;

/**
 * @ClassName: Immersible
 * @Author: Gracefulwind
 * @CreateDate: 2022/5/7
 * @Description: ---------------------------
 * 沉浸式获取需要适应的view的接口
 * @UpdateUser:
 * @UpdateDate: 2022/5/7
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public interface Immersible {
    View getStatusBarPaddingView();
    View getStatusBarMarginView();
}
