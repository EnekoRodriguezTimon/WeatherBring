package com.bring.weatherbring.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bring.weatherbring.api.model.WeatherData;
import com.bring.weatherbring.fragments.WeatherDetailFragment;
import com.bring.weatherbring.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherPagerAdapter  extends  FragmentPagerAdapter {
    private List<Weather> mDataList; // Reemplaza WeatherData con tu tipo de datos

    public WeatherPagerAdapter(FragmentManager fm, List<Weather> dataList) {
        super(fm);
        mDataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherDetailFragment.newInstance(mDataList.get(position));
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }
}