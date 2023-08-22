package com.bring.weatherbring.api;


import com.bring.weatherbring.api.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("forecast")
    Call<WeatherData> getWeatherForecast(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiid,
            @Query("units") String units
    );
}
