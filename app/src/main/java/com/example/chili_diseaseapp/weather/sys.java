package com.example.chili_diseaseapp.weather;

public class sys {

    public int sunrise;
    public int sunset;

    public sys(int sunrise, int sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;

    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
