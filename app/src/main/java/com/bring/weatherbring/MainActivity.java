package com.bring.weatherbring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bring.weatherbring.model.Location;
import com.bring.weatherbring.util.PermissionsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnLocationChangeListener{
    PermissionsManager permissionsManager = new PermissionsManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fbNoCities  = findViewById(R.id.fbNoCities);
        fbNoCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMaps();
            }
        });
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

    }
}