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

/**
 * ================================================
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 * <p>
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 * <p>
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 * <p>
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 * <p>
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.4">RouterHub wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 18:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP_HOME = "/app";//宿主 App 组件
    String COPY_HOME = "/copy";//copy组件
    String DEFAULT = "/default";//default组件
    String ZHIHU_HOME = "/zhihu";//知乎组件
    String GANK_HOME = "/gank";//干货集中营组件
    String GOLD_HOME = "/gold";//稀土掘金组件
    String TEST_DAGGER_HOME = "/test_dagger";//测试dagger组件
    String WEATHER_HOME = "/weather";//天气组件
    String READER_HOME = "/novels";//小说组件
    String WRITE_HOME = "/smart_write";//智能手写组件
    String NEW_WRITE_HOME = "/new_write";//智能手写组件-新
    String OCR_HOME = "/ocr";//ocr相关测试

    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";

//====================================================================================================
    /**
     * 宿主 App 分组
     */
    String APP_SPLASHACTIVITY = APP_HOME + "/SplashActivity";
    String APP_MAINACTIVITY = APP_HOME + "/MainActivity";

//====================================================================
    /**
     * 通用基础组件组件
     * */
    interface Default{
        String BASE = DEFAULT;
        String DEFAULT_ACTIVITY = BASE + "/DefaultActivity";
        String DEFAULT_FRAGMENT = BASE + "/DefaultFragment";
    }

//====================================================================================================

//====================================================================
    /**
     * copy组件
     * */
    interface Copy{
        String BASE = COPY_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
    }

//====================================================================================================
    /**
     * 知乎分组
     */
    interface ZhiHu{
        String ZHIHU_SERVICE_ZHIHUINFOSERVICE = ZHIHU_HOME + SERVICE + "/ZhihuInfoService";

        String ZHIHU_HOMEACTIVITY = ZHIHU_HOME + "/HomeActivity";
        String ZHIHU_DETAILACTIVITY = ZHIHU_HOME + "/DetailActivity";
    }


//====================================================================================================
    /**
     * 干货集中营分组
     */
    interface Gank {
        String BASE = GANK_HOME;
        String GANK_SERVICE_GANKINFOSERVICE = BASE + SERVICE + "/GankInfoService";

        String GANK_HOMEACTIVITY = BASE + "/HomeActivity";
    }


//====================================================================================================
    /**
     * 稀土掘金分组
     */
    interface Gold {
        String BASE = GOLD_HOME;

        String GOLD_SERVICE_GOLDINFOSERVICE = BASE + SERVICE + "/GoldInfoService";

        String GOLD_HOMEACTIVITY = BASE + "/HomeActivity";
        String GOLD_DETAILACTIVITY = BASE + "/DetailActivity";
    }

//====================================================================================================
    /**
     * 测试dagger分组
    */
    interface TestDagger {
        String BASE = TEST_DAGGER_HOME;
//        String SERVICE_INFOSERVICE = BASE + SERVICE + "/InfoService";

        String HOME_ACTIVITY = BASE + "/HomeActivity";
        String DAGGER_PAGE_1_ACTIVITY = BASE + "/DaggerPage1Activity";
        String DAGGER_PAGE_2_ACTIVITY = BASE + "/DaggerPage2Activity";

        interface Server{
            String INFO_SERVICE = BASE + SERVICE + "/InfoService";
        }
    }

//====================================================================================================
    /**
     * 天气分组
     */
    interface Weather {
        String BASE = WEATHER_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
        String WEATHER_ACTIVITY = BASE + "/WeatherActivity";
        String WEATHER_FRAGMENT = BASE + "/WeatherFragment";

        interface Server{
            String COMMON_SERVER = BASE + SERVICE + "/CommonService";
        }

    }

//====================================================================================================
    /**
     * 小说阅读器
     */
    interface Reader {
        String BASE = READER_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
        String DOODLE_ACTIVITY = BASE + "/DoodleActivity";
        String HAND_WRITE_ACTIVITY = BASE + "/HandWriteActivity";
        String TEST_GESTURE_ACTIVITY = BASE + "/TestGestureActivity";
        String HAND_NOTE_ACTIVITY = BASE + "/HandNoteActivity";
        String TEST_TEXT_ACTIVITY = BASE + "/TestTextActivity";
        String READER_MAIN_ACTIVITY = BASE + "/ReaderMainActivity";

        //=======================
        String BOOK_SHELF_FRAGMENT = BASE + "/BookShelfFragment";
        String BOOK_MALL_FRAGMENT = BASE + "/BookMallFragment";
        String BOOK_SEARCH_FRAGMENT = BASE + "/BookSearchFragment";
        String BOOK_ACTIVITY_FRAGMENT = BASE + "/BookActivityFragment";
        String BOOK_MINE_FRAGMENT = BASE + "/BookMineFragment";


        interface Server{
            String COMMON_SERVER = BASE + SERVICE + "/CommonService";
        }

    }
//====================================================================
    /**
     * 智能手写组件
     * */
    interface SmartWrite{
        String BASE = WRITE_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
        String SMART_HAND_NOTE_ACTIVITY = BASE + "/SmartHandNoteActivity";
        String WRITE_TEST_ACTIVITY = BASE + "/WriteTestActivity";
    }

//====================================================================
    /**
     * newWrite组件
     * */
    interface NewWrite{
        String BASE = NEW_WRITE_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
        String TEST_NEW_WRITE_ACTIVITY = BASE + "/TestNewWriteActivity";
        String TEST_XUNFEI_ACTIVITY = BASE + "/TestXunfeiActivity";
    }

//====================================================================
    /**
     * ocr组件
     * */
    interface Ocr{
        String BASE = OCR_HOME;
        String HOME_ACTIVITY = BASE + "/HomeActivity";
    }
}
