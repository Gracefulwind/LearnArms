package com.gracefulwind.learnarms.reader.entity;

import androidx.annotation.DrawableRes;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @ClassName: SimpleTabEntity
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SimpleTabEntity implements CustomTabEntity {

    String tabTitle;
    @DrawableRes
    int tabSelectedIcon;
    @DrawableRes
    int tabUnselectedIcon;

    public SimpleTabEntity(String tabTitle, @DrawableRes int tabSelectedIcon, @DrawableRes int tabUnselectedIcon) {
        this.tabTitle = tabTitle;
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public @DrawableRes int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public @DrawableRes int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
