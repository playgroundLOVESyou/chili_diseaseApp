package com.example.chili_diseaseapp.weather;

import java.util.List;

public class weatherdata {

    private List<weather> weather;

    private main main;

    private sys sys;
    private String name;

    private  String sunrise;

    private String sunset;

    public weatherdata(List<com.example.chili_diseaseapp.weather.weather> weather, com.example.chili_diseaseapp.weather.main main, com.example.chili_diseaseapp.weather.sys sys, String name, String sunrise, String sunset) {
        this.weather = weather;
        this.main = main;
        this.sys = sys;
        this.name = name;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public List<com.example.chili_diseaseapp.weather.weather> getWeather() {
        return weather;
    }

    public void setWeather(List<com.example.chili_diseaseapp.weather.weather> weather) {
        this.weather = weather;
    }

    public com.example.chili_diseaseapp.weather.main getMain() {
        return main;
    }

    public void setMain(com.example.chili_diseaseapp.weather.main main) {
        this.main = main;
    }

    public com.example.chili_diseaseapp.weather.sys getSys() {
        return sys;
    }

    public void setSys(com.example.chili_diseaseapp.weather.sys sys) {
        this.sys = sys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
