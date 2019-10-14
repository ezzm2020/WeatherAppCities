package com.example.weatherappcities.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet {

    Context context;

    public CheckInternet(Context context) {
        this.context = context;
    }
    public boolean isconnect()
    {
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             if(manager !=null)
             {
                 @SuppressLint("MissingPermission") NetworkInfo info=manager.getActiveNetworkInfo();
                 if(info !=null&&info.isConnected())
                 {
                     return  true;
                 }
             }

        return  false;
    }
}
