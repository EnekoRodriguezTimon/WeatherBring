package com.bring.weatherbring.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

public class PermissionsManager {

    private final Context context;
    private static final int REQUEST_CODE = 123;

    private static final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public PermissionsManager(Context context) {
        this.context = context;
    }

    public boolean checkPermissions() {

        if (checkPermissions(permissions)) {
            return true;
        } else {
            // Ask for permissions
            ActivityCompat.requestPermissions((Activity) this.context, permissions, REQUEST_CODE);
            return false;
        }
    }


    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true; //all permissions granted
    }


}
