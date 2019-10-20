package com.example.weatherappcities.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.example.weatherappcities.REpository.WeatherRepository;
import com.example.weatherappcities.ViewModel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Common {
    private static final String CITY_NAME = "London";
    public static Location currentLocation;


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static final String UNITS = "metric";
    public static final String APP_ID = "c6afdab60aa89481e297e0a4f19af055";


    static Context context;
//    static LocationManager locationManager = (LocationManager)
//            Objects.requireNonNull(context).getSystemService(Context.LOCATION_SERVICE);
//
//    @SuppressLint("MissingPermission")
//    static Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//    public static String lat = String.valueOf(location.getLatitude());
//    public static String lot = String.valueOf(location.getLongitude());


    public Common(Context context) {
        this.context = context;
    }

    public Common() {

    }


    public static String convertunixtodate(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static String convertunixtoHour(long dt) {
        Date date = new Date(dt * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String format = simpleDateFormat.format(date);
        return format;
    }

}
