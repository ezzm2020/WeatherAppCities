package com.example.weatherappcities.REpository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.weatherappcities.R;
import com.example.weatherappcities.model.WeatherResult;
import com.example.weatherappcities.model.models;

public class ModelRepository {

    static ModelRepository instance;

    List<models> modelsList = new ArrayList<>();
    List<models> modelsDET = new ArrayList<>();

    public static ModelRepository getInstance() {
        if (instance == null) {
            instance = new ModelRepository();
        }
        return instance;
    }


    public MutableLiveData<List<models>> getModelsRP() {
        addModelsData();
        MutableLiveData<List<models>> data = new MutableLiveData<>();
        data.setValue(modelsList);
        return data;
    }

    public MutableLiveData<List<models>> getDETsRP() {
        AddLATALOn();
        MutableLiveData<List<models>> data = new MutableLiveData<>();
        data.setValue(modelsDET);
        return data;
    }

    public void AddLATALOn() {
        String[] lat = new String[]{"44.549999", "28", "29", "44.599998", "70", "28", "55.796391", "20", "44.416668", "8.598333", "55.423332", "37.611111"};
        String[] lon = new String[]{"34.283333", "37.666668", "84.633331", "76", "33.900002", "85.416664", "77", "85.416664", "37.611111", "33.733334", "71.144997", "38.545555"};

        for (int i = 0; i < lat.length; i++) {
            modelsDET.add(new models(lat[i], lon[i]));
        }
    }

    public void addModelsData() {
        modelsList.add(new models("Hurzuf,UA"));
        modelsList.add(new models("Novinki,RU"));
        modelsList.add(new models("Gorkhā,NP"));
        modelsList.add(new models("Paris,FR"));
        modelsList.add(new models("Holubynka,NP"));
        modelsList.add(new models("Bāgmatī ZoneNP"));
        modelsList.add(new models("Hurzuf,UA"));
        modelsList.add(new models("Hurzuf,UA"));
        modelsList.add(new models("Gørding,DK"));
        modelsList.add(new models("Hurzuf,UA"));
        modelsList.add(new models("Vinogradovo,RU"));
        modelsList.add(new models("Hurzuf,UA"));
        modelsList.add(new models("Tistrup Stationsby,EG"));
    }
}
