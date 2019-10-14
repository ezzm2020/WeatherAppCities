package com.example.weatherappcities.model;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    public float h3;

    public Rain() {
    }

    public float getH3() {
        return h3;
    }

    public void setH3(float h3) {
        this.h3 = h3;
    }
}
