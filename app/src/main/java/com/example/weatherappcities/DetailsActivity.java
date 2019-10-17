package com.example.weatherappcities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappcities.Retrofi.RetrofitClient;
import com.example.weatherappcities.Retrofi.WeatherAPI;
import com.example.weatherappcities.ViewModel.MainViewModel;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.WeatherResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import com.example.weatherappcities.model.models;
public class DetailsActivity extends AppCompatActivity {

    TextView lattext,lontext;

    TextView txtname, txthumudity, winds, presure, sunrise, sunsite, GEo;

    WeatherAPI weatherAPI;
    List<models>modelsList;

    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

         modelsList=new ArrayList<>();
        txthumudity = findViewById(R.id.txthumudityd);

        winds = findViewById(R.id.txtwidsd);
        presure = findViewById(R.id.txtpressuredd);

        sunrise = findViewById(R.id.txt_sunrised);
        sunsite = findViewById(R.id.txtsunsited);
        GEo = findViewById(R.id.txtGEod);


        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        weatherAPI = retrofit.create(WeatherAPI.class);


        int pos=getIntent().getExtras().getInt("pogif");

        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.initt();
        mainViewModel.getModelslv().observe(this, new Observer<List<models>>() {
            @Override
            public void onChanged(List<models> models) {

                winds.setText(models.get(pos).getCityname());

            }
        });

        String []lat=new String[]{"44.549999", "28", "29", "44.599998","70","28","55.796391", "20", "44.416668", "8.598333","55.423332","37.611111"};
        String []lon=new String[]{"34.283333","37.666668","84.633331","76","33.900002","85.416664","77","85.416664","37.611111", "33.733334","71.144997","38.545555"};

       for (int i=0;i<lat.length;i++)
       {
           modelsList.add(new models(lat[i],lon[i]));
       }


       Observable<WeatherResult> observable = weatherAPI.getWeatherByLating(modelsList.get(pos).getLat(),modelsList.get(pos).getLon(), Common.APP_ID, Common.UNITS);

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
                        Toast.makeText(DetailsActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });


    }
    public void getData() {

        //Getting the Location Start





    }
}
