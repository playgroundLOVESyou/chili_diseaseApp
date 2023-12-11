package com.example.chili_diseaseapp;

import com.example.chili_diseaseapp.weather.weatherdata;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceAPI {
@GET("data/2.5/weather")
    Call<weatherdata> getData(
      @Query("q") String q,
      @Query("appid") String APIKEY,
      @Query("units") String units
    );
}
