package com.example.weatherappcities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappcities.Retrofi.RetrofitClient;
import com.example.weatherappcities.Retrofi.WeatherAPI;
import com.example.weatherappcities.ViewModel.MainViewModel;
import com.example.weatherappcities.ViewModel.WeatherViewModel;
import com.example.weatherappcities.model.CheckInternet;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.WeatherResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrentActivity extends AppCompatActivity {

    RelativeLayout constraintLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    TextView txtname, txthumudity, winds, presure, sunrise, sunsite, GEo;


    WeatherAPI weatherAPI;
    CompositeDisposable compositeDisposable;

    RxPermissions rxPermissions;

    WeatherViewModel weatherViewModel;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        constraintLayout = findViewById(R.id.rood_views);

        txthumudity = findViewById(R.id.txthumudity);
        txtname = findViewById(R.id.txtnamecurrent);
        winds = findViewById(R.id.txtwids);
        presure = findViewById(R.id.txtpressured);

        sunrise = findViewById(R.id.txt_sunrise);
        sunsite = findViewById(R.id.txtsunsite);
        GEo = findViewById(R.id.txtGEo);


        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.initt();


        weatherViewModel.getWeatherlv().observe(this, articleResponse -> {
            if (articleResponse != null) {

                presure.setText(new StringBuilder(String.valueOf(articleResponse.getMain().getTemp())).append(articleResponse.getName().toString()));

                txthumudity.setText(new StringBuilder(String.valueOf(articleResponse.getMain().getTemp())).append("'c").toString());

                txtname.setText(articleResponse.getName());
                GEo.setText(new StringBuilder(String.valueOf(articleResponse.getCoord().getLat())).append(":").append(articleResponse.getCoord().getLon()));

                sunrise.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(articleResponse.getSys().sunrise))));
                sunsite.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(articleResponse.getSys().sunset))));


            }
        });

        txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SEarchActivity.class);
                startActivity(intent);
            }
        });

//        compositeDisposable = new CompositeDisposable();
//        Retrofit retrofit = RetrofitClient.getRetrofitClient();
//        weatherAPI = retrofit.create(WeatherAPI.class);
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {

                            Toast.makeText(CurrentActivity.this, "" + report.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        Toast.makeText(CurrentActivity.this, "" + token.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).check();
//
//        CheckInternet internet = new CheckInternet(getBaseContext());
//        boolean check = internet.isconnect();
//        if (!check) {
//            String name = "برجاء التاكد من الاتصال بالانترنت";
//            Toast.makeText(CurrentActivity.this, name, Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
//            LocationManager locationManager = (LocationManager) Objects.requireNonNull(getBaseContext()).getSystemService(Context.LOCATION_SERVICE);
//
//            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//
//             //getData();
//            getWeatherData();
//
//        }
    }

    public void getWeatherData() {
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getBaseContext()).getSystemService(Context.LOCATION_SERVICE);

        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        String Latitude = String.valueOf(location.getLatitude());
        String Longitude = String.valueOf(location.getLongitude());

        Call call = weatherAPI.getCurrentWeatherData(Latitude, Longitude, Common.APP_ID, Common.UNITS);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.code() == 200) {
                    WeatherResult weatherResult = (WeatherResult) response.body();
                    assert weatherResult != null;

                    presure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append(weatherResult.getName().toString()));

                    txthumudity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("'c").toString());

                    txtname.setText(weatherResult.getName());
                    GEo.setText(new StringBuilder(String.valueOf(weatherResult.getCoord().getLat())).append(":").append(weatherResult.getCoord().getLon()));

                    sunrise.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(weatherResult.getSys().sunrise))));
                    sunsite.setText(new StringBuilder(String.valueOf(Common.convertunixtodate(weatherResult.getSys().sunset))));

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {

                txtname.setText(t.getMessage());

            }
        });
    }

    public void getData() {

        BuildLocationRequest();
        BuildLocationCallBack();

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
                        Toast.makeText(CurrentActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


//    private void getWeather() {
//
//        compositeDisposable.add(weatherAPI.getWeatherByLating(String.valueOf(Common.currentLocation.getLatitude()),
//                String.valueOf(Common.currentLocation.getLongitude()),
//                Common.APP_ID,
//                Common.UNITS
//                ).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<WeatherResult>() {
//                            @Override
//                            public void accept(WeatherResult weatherResult) throws Exception {
//
//                                txtname.setText(weatherResult.getName());
////                        description.setText(new StringBuilder("Weather en").append(weatherResult.getName().toString()));
//                                presure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append(weatherResult.getName().toString()));
//
//                                txthumudity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("'c").toString());
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText(getBaseContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//        );
//    }

    private void BuildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(8000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void BuildLocationCallBack() {

        locationCallback = new LocationCallback() {


            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.currentLocation = locationResult.getLastLocation();
                Toast.makeText(CurrentActivity.this, "lan" + locationResult.getLastLocation().getLatitude() + "lon" + locationResult.getLastLocation().getAltitude(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
