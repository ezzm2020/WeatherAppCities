package com.example.weatherappcities.model;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    private static final String CITY_NAME = "London";

    @Override
    public int hashCode() {
        return super.hashCode();
    }

   public static final String UNITS = "metric";
    public static final String APP_ID = "c6afdab60aa89481e297e0a4f19af055";
    public static Location currentLocation=null;


    public static String convertunixtodate(long dt)
    {
        Date date=new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String format=simpleDateFormat.format(date);
        return format;
    }
    public static String convertunixtoHour(long dt)
    {
        Date date=new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        String format=simpleDateFormat.format(date);
        return format;
    }

}
