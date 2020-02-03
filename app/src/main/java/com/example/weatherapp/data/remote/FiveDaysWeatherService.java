package com.example.weatherapp.data.remote;

import com.example.weatherapp.data.model.WeatherFiveDays;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FiveDaysWeatherService {
    @GET("/data/2.5/forecast")
    Call<WeatherFiveDays> getFiveDaysWeather(@QueryMap Map<String,String> params);
}
