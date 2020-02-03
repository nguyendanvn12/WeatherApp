package com.example.weatherapp.data.remote;

public class ApiUtils {
    public static final String BASE_URL = "http://api.openweathermap.org";

    public static CurrentWeatherService getCurrentWeatherService() {
        return RetrofitClient.getClient(BASE_URL).create(CurrentWeatherService.class);
    }
    public static FiveDaysWeatherService getFiveDaysWeatherService() {
        return RetrofitClient.getClient(BASE_URL).create(FiveDaysWeatherService.class);
    }}
