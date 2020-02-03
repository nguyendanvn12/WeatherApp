package com.example.weatherapp.data.remote;

import com.example.weatherapp.data.model.CurrentWeather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CurrentWeatherService {
     @GET("/data/2.5/weather")
     Call<CurrentWeather> getCurrentWeather(@QueryMap Map<String,String> params);

}
