package com.bring.weatherbring.api;

import com.bring.weatherbring.api.model.WeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private GetDataService getDataService;

    public RetrofitClientInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getDataService = retrofit.create(GetDataService.class);
    }

    public Call<WeatherData> getWeatherForecast(double latitude, double longitude, String apiKey, String units) {
        return getDataService.getWeatherForecast(latitude, longitude, apiKey, units);
    }
}
