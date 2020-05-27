package com.gracefulwind.learnarms.module_weather.app.api.service;

import com.gracefulwind.learnarms.module_weather.mvp.model.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.WeatherEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.gracefulwind.learnarms.module_weather.app.api.Api.WEATHER_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.IDENTIFICATION_IGNORE;

/**
 * @ClassName: WeatherService
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/27 15:58
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/27 15:58
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public interface WeatherService {
    /**
     * 天气API
     */
//    @GET("weather/now"  + IDENTIFICATION_IGNORE)
    @Headers({DOMAIN_NAME_HEADER + WEATHER_DOMAIN_NAME})
    @GET("weather/now")
    Observable<WeatherEntity> getWeather(@Query("location") String location, @Query("key") String key);


    @GET("movie/in_theaters")
    Observable<DoubanMovieBean> getMovieSubjectRx(@Query("apikey") String apiKey, @Query("start") int start, @Query("count") int count);
}
