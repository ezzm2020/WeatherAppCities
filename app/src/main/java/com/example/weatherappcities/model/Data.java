package com.example.weatherappcities.model;

public class Data {
  public   String cityname;

    public Data(String cityname) {
        this.cityname = cityname;
    }

    public Data() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
