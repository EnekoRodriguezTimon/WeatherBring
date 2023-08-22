package com.bring.weatherbring.fragments;

import android.media.Image;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bring.weatherbring.R;
import com.bring.weatherbring.adapters.WeatherPagerAdapter;
import com.bring.weatherbring.api.RetrofitClientInstance;
import com.bring.weatherbring.api.model.Main;
import com.bring.weatherbring.api.model.WeatherData;
import com.bring.weatherbring.api.model.WeatherItem;
import com.bring.weatherbring.model.Location;
import com.bring.weatherbring.model.Weather;
import com.bring.weatherbring.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {
    public static final String ARG_ITEM = "selected_location";

    private Location selectedLocation;

    private RetrofitClientInstance retrofitClientInstance;

    private List<Weather> weatherList;

    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        weatherList = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null) {
            selectedLocation = (Location) args.getSerializable("location");
        }

        retrofitClientInstance = new RetrofitClientInstance();



        //Api key and base url should be stored in gradle.properties
        Call<WeatherData> call = retrofitClientInstance.getWeatherForecast(selectedLocation.getLatitude(), selectedLocation.getLongitude(), "c6e381d8c7ff98f0fee43775817cf6ad", "metric");
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    WeatherData weatherData = response.body();
                    if (weatherData != null && weatherData.getList() != null && !weatherData.getList().isEmpty()) {

                        List<WeatherItem> weatherItemList = weatherData.getList();

                        for (int i=1;i<=40;i++){
                            WeatherItem wi = weatherItemList.get(i-1);
                            if(i==0 || i%8==0){
                                DateUtils du =new DateUtils();
                                Weather w = new Weather(wi.getDt_txt().substring(0, 10),wi.getMain().getTemp(),wi.getMain().getHumidity(),wi.getWind().getSpeed(), wi.getClouds().getAll() );
                                weatherList.add(w);
                            }
                        }
                        updateView();

                    }
                } else {


                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d("error","error"+t.getMessage());
            }
        });

        viewPager = view.findViewById(R.id.vpWeather);
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getChildFragmentManager(), weatherList);
        viewPager.setAdapter(adapter);


        ImageView ivWeatherFragmentNext =  view.findViewById(R.id.ivWeatherFragmentNext);
        ImageView ivWeatherFragmentPrevious =  view.findViewById(R.id.ivWeatherFragmentPrevious);
        ImageView ivWeatherClose =  view.findViewById(R.id.ivWeatherClose);

        ivWeatherFragmentNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDay();
            }
        });

        ivWeatherFragmentPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousDay();
            }
        });

        ivWeatherClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        TextView tvWeatherTitle = view.findViewById(R.id.tvWeatherTitle);

        if(selectedLocation.getCityName()!=null) {
            tvWeatherTitle.setText(selectedLocation.getCityName());
        }else{
            tvWeatherTitle.setText(String.valueOf(selectedLocation.getLatitude()));
        }

        return view;
    }


    private void updateView(){
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getChildFragmentManager(), weatherList);
        viewPager.setAdapter(adapter);

    }

    private void nextDay(){
        int currentPosition = viewPager.getCurrentItem();
        int nextPosition = currentPosition + 1;

        if (nextPosition < viewPager.getAdapter().getCount()) {
            viewPager.setCurrentItem(nextPosition);
        }
    }
    private void previousDay(){
        int currentPosition = viewPager.getCurrentItem();
        int nextPosition = currentPosition - 1;

        if (nextPosition >= 0) {
            viewPager.setCurrentItem(nextPosition);
        }
    }

    private void closeFragment(){
        getFragmentManager().beginTransaction().remove(this).commit();
    }


}