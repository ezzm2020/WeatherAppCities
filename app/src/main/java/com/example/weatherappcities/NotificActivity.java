package com.example.weatherappcities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappcities.Retrofi.RetrofitClient;
import com.example.weatherappcities.Retrofi.WeatherAPI;
import com.example.weatherappcities.model.AMReceiver;
import com.example.weatherappcities.model.AMService;
import com.example.weatherappcities.model.CheckInternet;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.WeatherResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NotificActivity extends AppCompatActivity {


    TextView txtname, txthumudity, winds, presure, sunrise, sunsite, GEo;


    WeatherAPI weatherAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notific);

        txtname = findViewById(R.id.txtnamecitys);
        txthumudity = findViewById(R.id.txthumuditys);

        winds = findViewById(R.id.txtwidss);
        presure = findViewById(R.id.txtpressureds);

        sunrise = findViewById(R.id.txt_sunrises);
        sunsite = findViewById(R.id.txtsunsites);
        GEo = findViewById(R.id.txtGEos);

        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        weatherAPI = retrofit.create(WeatherAPI.class);

            getData();

    }

    public void startServices() {

        Intent intent = new Intent(this, AMReceiver.class);
        intent.putExtra("citynameR", txtname.getText().toString());
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pendingIntent);
        startService(intent);
    }

    public void stopServices() {
        Intent intent = new Intent(this, AMService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  startServices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void getData() {

        //Getting the Location Start
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getBaseContext()).getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        String Latitude = String.valueOf(location.getLatitude());
        String Longitude = String.valueOf(location.getLongitude());

        Observable<WeatherResult> observable = weatherAPI.getWeatherByLating(Latitude, Longitude, Common.APP_ID, Common.UNITS);
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        startServices();

                        presure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append(weatherResult.getName().toString()));

                        txthumudity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("'c").toString());

                        txtname.setText(weatherResult.getName());
                        GEo.setText(new StringBuilder(String.valueOf(weatherResult.getCoord().getLat())).append(":").append(weatherResult.getCoord().getLon()));

                        sunrise.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(weatherResult.getSys().sunrise))));
                        sunsite.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(weatherResult.getSys().sunset))));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        presure.setText(throwable.getMessage() + "");
                        Toast.makeText(getBaseContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
