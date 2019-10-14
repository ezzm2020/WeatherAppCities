package com.example.weatherappcities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappcities.Retrofi.RetrofitClient;
import com.example.weatherappcities.Retrofi.WeatherAPI;
import com.example.weatherappcities.model.Common;
import com.example.weatherappcities.model.WeatherResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.label305.asynctask.SimpleAsyncTask;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView txtname, txthumudity, sunrise, temprature, description, presure;
    public List<String> citylist;
    MaterialSearchBar materialSearchBar;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    CompositeDisposable compositeDisposable;
    WeatherAPI service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        service = retrofit.create(WeatherAPI.class);



        description = findViewById(R.id.txt_decription);
        txtname = findViewById(R.id.txt_cityname);
        txthumudity = findViewById(R.id.txt_humidity);
        sunrise = findViewById(R.id.txt_sunrise);
        temprature = findViewById(R.id.txt_temprature);
        presure = findViewById(R.id.txtpresure);

        linearLayout = findViewById(R.id.weather_panel);
        progressBar = findViewById(R.id.loaindg);
        materialSearchBar = findViewById(R.id.searchbar);
        materialSearchBar.setEnabled(true);
           progressBar.setVisibility(View.INVISIBLE);
//        try {
//            try {
//                StringBuilder builder = new StringBuilder();
//                InputStream stream = getResources().openRawResource(R.raw.citylist);
//                GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
//
//                InputStreamReader reader = new InputStreamReader(gzipInputStream);
//                BufferedReader bufferedReader = new BufferedReader(reader);
//                String Read;
//                while ((Read = bufferedReader.readLine()) != null) {
//                    builder.append(Read);
//                }
//                citylist = new Gson().fromJson(builder.toString(), new TypeToken<List<String>>() {
//                }.getType());
//
//
//                materialSearchBar.addTextChangeListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        List<String> suggestlist = new ArrayList<>();
//                        for (String search : citylist) {
//                            if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
//                                suggestlist.add(search);
//                        }
//                        materialSearchBar.setLastSuggestions(suggestlist);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                });
//                materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//                    @Override
//                    public void onSearchStateChanged(boolean enabled) {
//
//                    }
//
//                    @Override
//                    public void onSearchConfirmed(CharSequence text) {
//
//                       getWeather(text.toString());
//                        materialSearchBar.setLastSuggestions(citylist);
//                    }
//
//                    @Override
//                    public void onButtonClicked(int buttonCode) {
//
//                    }
//                });
//                materialSearchBar.setLastSuggestions(citylist);
//
//
//
////                List<String> suggestlist = new ArrayList<>();
////                for (String search : citylist) {
////                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
////                        suggestlist.add(search);
////                }
////                materialSearchBar.setLastSuggestions(suggestlist);
//
//            } catch (IOException e) {
//
//                e.printStackTrace();
//                Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

     new  LoadCities().execute();



    }
    private class LoadCities extends SimpleAsyncTask<List<String>> {

        @Override
        protected List<String> doInBackgroundSimple() {

            citylist = new ArrayList<>();
            try {
                StringBuilder builder = new StringBuilder();
                InputStream is = getResources().openRawResource(R.raw.citylist);
                GZIPInputStream gzipInputStream = new GZIPInputStream(is);

                InputStreamReader reader = new InputStreamReader(gzipInputStream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String Read;
                while ((Read = bufferedReader.readLine()) != null) {
                    builder.append(Read);
                }
                citylist = new Gson().fromJson(String.valueOf(builder), new TypeToken<List<String>>(){}.getType());
            } catch (IOException e) {

                e.printStackTrace();
                Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
            return citylist;
        }

        @Override
        protected void onSuccess(final List<String> listcity) {
            super.onSuccess(listcity);
           // materialSearchBar.setEnabled(false);
            materialSearchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    List<String> suggestlist = new ArrayList<>();
                    for (String search : listcity) {
                        if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                            suggestlist.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggestlist);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {

                    getWeather(text.toString());
                    materialSearchBar.setLastSuggestions(listcity);
                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });
            materialSearchBar.setLastSuggestions(listcity);
            //progressBar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

        }
    }

    private void getWeather(String cityname) {
        compositeDisposable.add(service.getWeatherByCity(cityname
                ,
                Common.APP_ID
                ,
                Common.UNITS
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<WeatherResult>() {
                            @Override
                            public void accept(WeatherResult weatherResult) throws Exception {

                                txtname.setText(weatherResult.getName());
                                description.setText(new StringBuilder("Weather en").append(weatherResult.getName().toString()));
                                temprature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append(weatherResult.getName().toString()));

                                linearLayout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getBaseContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }

//    @Override
//    public void onDestroy() {
//        compositeDisposable.clear();
//        super.onDestroy();
//
//    }
//
//    @Override
//    public void onStop() {
//        compositeDisposable.clear();
//
//        super.onStop();
//    }
}
