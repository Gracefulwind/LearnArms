package com.gracefulwind.learnarms.module_weather.mvp.contract;

import com.gracefulwind.learnarms.module_weather.mvp.model.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.WeatherEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2020 17:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface WeatherFragmentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
//        void showSomeThing(String str);
        void showWeather(String weatherJson);
        void setCitySearchName(String citySearchName);
        String getCitySearchName();
    }
    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel{
        Observable<WeatherEntity> getWeather(String city);
        Observable<DoubanMovieBean> getDouban(int start, int count);
    }
}