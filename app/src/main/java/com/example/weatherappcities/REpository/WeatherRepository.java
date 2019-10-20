package com.example.weatherappcities.REpository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherappcities.Retrofi.RetrofitClient;
import com.example.weatherappcities.Retrofi.WeatherAPI;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.WeatherResult;
import com.example.weatherappcities.model.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    static WeatherRepository instance;

    private  WeatherAPI apiRequest;

    public static WeatherRepository getInstance() {
        if (instance == null) {
            instance = new WeatherRepository();
        }
        return instance;
    }


    public WeatherRepository() {
        apiRequest = RetrofitClient.getRetrofitClient().create(WeatherAPI.class);
    }


    public MutableLiveData<WeatherResult> getWeathersRP(String lat , String lon, String appid , String unit) {
        //   addModelsData();
        apiRequest = RetrofitClient.getRetrofitClient().create(WeatherAPI.class);

        MutableLiveData<WeatherResult> data = new MutableLiveData<>();
        // data.setValue(modelsList);
        apiRequest.getCurrentWeatherData(lat,lon,appid,unit)
                .enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.body() != null) {
                            data.setValue((WeatherResult) response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

}
