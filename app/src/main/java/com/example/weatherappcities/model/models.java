package com.example.weatherappcities.model;

public class models {
    String cityname;
    String lat;
    String lon;

    public models(String cityname) {
        this.cityname = cityname;
    }

    public models(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public models(String cityname, String lat, String lon) {
        this.cityname = cityname;
        this.lat = lat;
        this.lon = lon;
    }

    public models() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
