package com.example.weatherappcities.model;

import com.google.gson.annotations.SerializedName;

public class Clouds {
    @SerializedName("all")
    public float all;

    public Clouds() {
    }

    public float getAll() {
        return all;
    }

    public void setAll(float all) {
        this.all = all;
    }
}
