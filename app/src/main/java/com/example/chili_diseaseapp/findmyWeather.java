package com.example.chili_diseaseapp;

import static com.example.chili_diseaseapp.R.drawable.*;
import static com.example.chili_diseaseapp.R.drawable.rain_background;
import static com.example.chili_diseaseapp.R.drawable.snow_background;
import static com.example.chili_diseaseapp.R.drawable.sunny_background;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chili_diseaseapp.databinding.ActivityFindmyWeatherBinding;
import com.example.chili_diseaseapp.databinding.ActivityLocationViewerBinding;
import com.example.chili_diseaseapp.weather.main;
import com.example.chili_diseaseapp.weather.sys;
import com.example.chili_diseaseapp.weather.weather;
import com.example.chili_diseaseapp.weather.weatherdata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class findmyWeather extends AppCompatActivity {

    ActivityFindmyWeatherBinding binding;
    ActivityLocationViewerBinding binding2;
    TextView feeling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmy_weather);
        feeling = findViewById(R.id.condition);

        binding = ActivityFindmyWeatherBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

            // currentdaycode
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String currentDay = simpleDayFormat.format(date);
        binding.day.setText(currentDay);



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyy");
        String currentdate= simpleDateFormat.format(new Date());
        binding.date.setText(currentdate);


        fetchWeather("Philippines");



        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.searchvieweditText.getText().toString())){
                    binding.searchvieweditText.setError("please enter City");
                    return;

                }

                String City_Name = binding.searchvieweditText.getText().toString();
                fetchWeather(City_Name);
            }
        });
    }



    void fetchWeather(String cityname){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        Call<weatherdata> call =  interfaceAPI.getData(cityname,"efe9ab8fe58f9117ba01ce1e2e88a9d6", "metric");

        call.enqueue(new Callback<weatherdata>() {
            @Override
            public void onResponse(Call<weatherdata> call, Response<weatherdata> response) {

                if(response.isSuccessful()){
                    weatherdata dataweather = response.body();


                    List<weather> des = dataweather.getWeather();
                    main to = dataweather.getMain();
                    sys me = dataweather.getSys();





                    binding.temp.setText(String.valueOf(to.getTemp()));
                    binding.maxTemp.setText(String.valueOf(to.getTemp_max()));
                    binding.minTemp.setText(String.valueOf(to.getTemp()));
                    binding.wind.setText(String.valueOf(to.getPressure()));
                    binding.seaLevel.setText(String.valueOf(to.getSea_level()));
                    binding.humidity.setText(String.valueOf(to.getHumidity()));
                    binding.cityName.setText(dataweather.getName());
                    binding.weather.setText(String.valueOf(to.getFeels_like()));
                    binding.sunsrise.setText(String.valueOf(me.getSunrise()));
                    binding.sunset.setText(String.valueOf(me.getSunset()));




                   
                    for (weather data : des) {
                        String condition = data.getDescription();
                        binding.weather.setText(data.getDescription());
                        binding.condition.setText(data.getDescription());
                        binding.weather.setText(condition);
                        binding.condition.setText(condition);


                        if (condition.contains("clear sky") || condition.contains("sunny") || condition.contains("clear") ) {
                            binding.backgroundimage.setBackgroundResource(sunnybackground);
                            binding.animationweathericon.setAnimation(R.raw.sun);

                            // Handle other weather conditions here
                        } else if (condition.contains("partly clouds") || condition.contains("clouds") || condition.contains("few clouds") || condition.contains("overcast clouds") || condition.contains("mist") || condition.contains("foggy") || condition.contains("mostly clouds") || condition.contains("scattered clouds") || condition.contains("broken clouds")) {
                            binding.backgroundimage.setBackgroundResource(R.drawable.cloudbackground);
                            binding.animationweathericon.setAnimation(R.raw.cloud);

                        } else if (condition.contains("light Rain") || condition.contains("moderate rain") || condition.contains("heavy intensity rain") || condition.contains("very heavy rain") || condition.contains("extreme rain") || condition.contains("freezing rain") || condition.contains("light intensity shower rain") || condition.contains("shower rain") || condition.contains("heavy intensity shower rain") || condition.contains("ragged shower rain")  ) {
                            binding.backgroundimage.setBackgroundResource(rainbackground);
                            binding.animationweathericon.setAnimation(R.raw.rain);
                        } else if (condition.contains("Light Snow") || condition.contains("Moderate Snow") || condition.contains("Heavy Snow") || condition.contains("Blizzard")) {
                            binding.backgroundimage.setBackgroundResource(snow_background);
                            binding.animationweathericon.setAnimation(R.raw.snow);
                        }else if (condition.contains("thunderstorm with light rain") || condition.contains("thunderstorm with rain") || condition.contains("thunderstorm with heavy rain") || condition.contains("\tlight thunderstorm") || condition.contains("thunderstorm") || condition.contains("\theavy thunderstorm") || condition.contains("ragged thunderstorm") || condition.contains("thunderstorm with light drizzle") || condition.contains("thunderstorm with drizzle") || condition.contains("thunderstorm with heavy drizzle")) {
                            binding.backgroundimage.setBackgroundResource(R.drawable.cloudbackground);
                            binding.animationweathericon.setAnimation(R.raw.cloud);
                        }
                        binding.animationweathericon.playAnimation();

                    }


                }

            }

            @Override
            public void onFailure(Call<weatherdata> call, Throwable t) {

            }
        });




        }



    }
