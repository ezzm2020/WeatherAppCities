package com.example.weatherappcities.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappcities.REpository.ModelRepository;
import com.example.weatherappcities.REpository.WeatherRepository;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.Weather;
import com.example.weatherappcities.model.WeatherResult;

import java.util.List;
import java.util.Objects;

public class WeatherViewModel extends ViewModel {

    MutableLiveData<WeatherResult> weatherlv = new MutableLiveData<>();
    WeatherRepository modelRepository;


    public void initt() {
        if (modelRepository != null) {
            return;
        }
        modelRepository = WeatherRepository.getInstance();

        weatherlv = modelRepository.getWeathersRP("44.549999", "34.283333", Common.APP_ID, Common.UNITS);
    }

    public LiveData<WeatherResult> getWeatherlv() {
        return weatherlv;
    }

}
