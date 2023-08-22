package com.bring.weatherbring.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bring.weatherbring.R;
import com.bring.weatherbring.adapters.WeatherPagerAdapter;
import com.bring.weatherbring.model.Weather;

public class WeatherDetailFragment extends Fragment {
    public static WeatherDetailFragment newInstance(Weather weather) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("weather_data", weather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_detail, container, false);


        Bundle args = getArguments();
        if (args != null) {
            Weather data = args.getParcelable("weather_data");
            if (data != null) {
                TextView tvTemperature = view.findViewById(R.id.tvWeatherDetailTemperature);
                TextView tvHumidity = view.findViewById(R.id.tvWeatherDetailHumidity);
                TextView tvRain = view.findViewById(R.id.tvWeatherDetailRain);
                TextView tvWind = view.findViewById(R.id.tvWeatherDetailWind);

                tvTemperature.setText(String.valueOf(data.getTemp()));
                tvHumidity.setText(String.valueOf(data.getHumidity()));
                tvRain.setText(String.valueOf(data.getRain()));
                tvWind.setText(String.valueOf(data.getSpeed()));
            }
        }

        return view;
    }
}