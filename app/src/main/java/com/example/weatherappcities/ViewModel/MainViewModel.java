package com.example.weatherappcities.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappcities.REpository.ModelRepository;
import com.example.weatherappcities.model.WeatherResult;

import java.util.List;

import com.example.weatherappcities.model.models;

public class MainViewModel extends ViewModel {

    MutableLiveData<List<models>> modeolist = new MutableLiveData<>();

    MutableLiveData<List<models>> modedet = new MutableLiveData<>();

    ModelRepository modelRepository;

    public void initt() {
        if (modelRepository != null) {
            return;
        }

         modelRepository = ModelRepository.getInstance();
         modeolist = modelRepository.getModelsRP();
         modedet =modelRepository.getDETsRP();
    }

    public LiveData<List<models>> getModelslv() {
        return modeolist;
    }

    //arb
    public MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    //arb
    public void getmodeldt() {
        String name = setmodel().getCityname();
        mutableLiveData.setValue(name);
    }

    //arb
    public models setmodel() {
        return new models("Egypt");
    }


}
