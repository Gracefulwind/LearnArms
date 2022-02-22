package com.gracefulwind.learnarms.commonsdk.utils;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonsdk.base.DefaultFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;

/**
 * @ClassName: ArouterUtil
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class ARouterUtil {

    /**
     * 通过路由地址生成相应fragment，如果地址有误则生成default页面
     *
     * */
    public static Fragment getFragment(String path){
        Object targetFragment = ARouter.getInstance().build(path).navigation();
        if(null != targetFragment && targetFragment instanceof Fragment){
            return (Fragment) targetFragment;
        }
//        return (Fragment) ARouter.getInstance().build(RouterHub.Default.DEFAULT_FRAGMENT).navigation();
        //一直为null...为什么
//        return (Fragment) ARouter.getInstance().build("/degrade/fragment").navigation();
        return new DefaultFragment();
    }

}
