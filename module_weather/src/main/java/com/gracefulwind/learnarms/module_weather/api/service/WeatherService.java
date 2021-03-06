package com.gracefulwind.learnarms.module_weather.api.service;

import com.gracefulwind.learnarms.module_weather.app.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.gracefulwind.learnarms.module_weather.api.Api.WEATHER_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

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
    @GET("s6/weather/now")
    Observable<WeatherData> getWeather(@Query("location") String location, @Query("key") String key);

    /**
     *
     *
     * */
    @Headers({DOMAIN_NAME_HEADER + WEATHER_DOMAIN_NAME})
    @GET("s6/weather/forecast")
    Observable<WeatherData> get7DayWeather(@Query("location") String location, @Query("key") String key);

    @Headers({DOMAIN_NAME_HEADER + WEATHER_DOMAIN_NAME})
    @GET("s6/weather/{weatherType}")
    Observable<WeatherData> getWeatherByType(@Path ("weatherType") String weatherType, @Query("location") String location, @Query("key") String key);

//========================================================================================================================================
    //通过，get请求体拼接也是可以的，只是不推荐
    @Headers({DOMAIN_NAME_HEADER + WEATHER_DOMAIN_NAME})
    @GET("s6/weather/now?location=hangzhou&key=94c2ffc7db1949389f228612266fc7f8")
    @Deprecated
    Observable<WeatherData> getWeather();

//=======================================================================================================================
    @GET("v2/movie/in_theaters")
    Observable<DoubanMovieBean> getMovieSubjectRx(@Query("apikey") String apiKey, @Query("start") int start, @Query("count") int count);
}
