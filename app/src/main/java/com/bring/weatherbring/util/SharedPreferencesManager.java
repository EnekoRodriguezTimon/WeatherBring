package com.bring.weatherbring.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bring.weatherbring.model.Location;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPreferencesManager {
    private Context context;

    private static final String CITIES_KEY = "cities";

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void saveLocation(Location location) {
        Gson gson = new Gson();
        String locationJson = gson.toJson(location);

        SharedPreferences sharedPreferences = context.getSharedPreferences(CITIES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //adding the object with a unique key
        editor.putString(location.getLatitude() + ":" + location.getLongitude(), locationJson);
        editor.apply();
    }

    public void deleteLocation(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CITIES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(name);
        editor.apply();
    }

    public List<Location> getAllLocations() {
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = context.getSharedPreferences(CITIES_KEY, Context.MODE_PRIVATE);
        Map<String, ?> todasLasEntradas = sharedPreferences.getAll();

        List<Location> listaRecuperada = new ArrayList<>();

        //to get all the objects and parse them
        for (Map.Entry<String, ?> entry : todasLasEntradas.entrySet()) {
            String key = entry.getKey();
            String objectJson = sharedPreferences.getString(key, "");

            if (!objectJson.isEmpty()) {
                Location objetoRecuperado = gson.fromJson(objectJson, Location.class);
                listaRecuperada.add(objetoRecuperado);

            }
        }

        return listaRecuperada;
    }
}
