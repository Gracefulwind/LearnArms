/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gracefulwind.learnarms.commonsdk.core;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * ================================================
 * CommonSDK 的 Constants 可以定义公用的常量, 比如关于业务的常量或者正则表达式, 每个组件的 Constants 可以定义组件自己的私有常量
 * <p>
 * Created by JessYan on 30/03/2018 17:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class Constants {
    //电话号码正则
    public static String PHONE_REGULAR = "^1[3-9]\\d{9}$";

    public static Bitmap.Config bitmapQuality = Bitmap.Config.ARGB_4444;

    public static float a4Ratio = 297f / 210f;

    public interface KEY{
        String HEFENG_KEY = "94c2ffc7db1949389f228612266fc7f8";
        String DOUBAN_KEY = "0df993c66c0c636e29ecbb5344252a4a";
    }
}
