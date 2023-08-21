package com.bring.weatherbring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bring.weatherbring.adapters.LocationAdapter;
import com.bring.weatherbring.model.Location;
import com.bring.weatherbring.util.PermissionsManager;
import com.bring.weatherbring.util.SharedPreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnLocationChangeListener{
    private PermissionsManager permissionsManager = new PermissionsManager(this);
    private SharedPreferencesManager sharedPreferencesManager = new  SharedPreferencesManager(this);
    private List<Location> locationList;

    FloatingActionButton fbNoCities;
    FloatingActionButton fbAddCities;

    LocationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }

    private void setUpView() {
        fbNoCities  = findViewById(R.id.fbNoCities);
        fbAddCities = findViewById(R.id.fbAddCities);
        fbNoCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMaps();
            }
        });
        fbAddCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMaps();
            }
        });

        getLocations();
    }

    private void getLocations() {
        locationList = sharedPreferencesManager.getAllLocations();
        LinearLayout llAddCities = findViewById(R.id.llAddCities);

        if(!locationList.isEmpty()){
            llAddCities.setVisibility(View.GONE);
            fbAddCities.setVisibility(View.VISIBLE);
        }else{
            llAddCities.setVisibility(View.VISIBLE);
            fbAddCities.setVisibility(View.GONE);
        }

        //setup locations adapter
        adapter = new LocationAdapter(locationList);
        RecyclerView recyclerView = findViewById(R.id.rvLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private void navigateToMaps(){
        if(permissionsManager.checkPermissions()){
            MapsFragment mapsFragment = new MapsFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.flMaps, mapsFragment);

            transaction.addToBackStack(null);

            transaction.commit();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null) {
            sharedPreferencesManager.saveLocation(location);
        }
        getLocations();
    }
}