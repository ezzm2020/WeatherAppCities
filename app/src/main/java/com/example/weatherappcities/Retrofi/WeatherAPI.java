package com.example.weatherappcities.Retrofi;


import com.example.weatherappcities.model.WeatherResult;

import io.reactivex.Observable;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

//    @GET("/weather")
//    void getWeatherByLating(@Query("q") String city, @Query("units") String
//            unitType, Callback<WeatherResult> callback);

    @GET("weather")
    Observable<WeatherResult> getWeatherByLating(@Query("lat") String lat, @Query("lon") String
            lon, @Query("appid") String appid, @Query("units") String units);

    @GET("weather")
    Observable<WeatherResult> getWeatherByCity(@Query("q") String cityname
            , @Query("appid") String appid
            , @Query("units") String unit);
    @GET("weather")
    Observable<WeatherResult> getWeatherData(@Query("q") String city, @Query("appid") String appid);

}
