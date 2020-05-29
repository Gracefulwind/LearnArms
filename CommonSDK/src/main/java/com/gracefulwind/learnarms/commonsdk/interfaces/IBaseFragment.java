package com.gracefulwind.learnarms.commonsdk.interfaces;

/**
 * @ClassName: BaseFragmentInterface
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/27 10:32
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/27 10:32
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public interface IBaseFragment {
    default String getFragmentName(){
        return this.toString();
    }
}
