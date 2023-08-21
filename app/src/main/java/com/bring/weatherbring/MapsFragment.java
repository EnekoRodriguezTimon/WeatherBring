package com.bring.weatherbring;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bring.weatherbring.model.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {
    private GoogleMap mMap;
    private Button btSaveLocation;
    private Location locationToSave;

    public interface OnLocationChangeListener {
        void onLocationChanged(Location location);
    }

    private OnLocationChangeListener locationChangeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        btSaveLocation = view.findViewById(R.id.btSaveLocation);
        Button ivCloseFragment = view.findViewById(R.id.ivCloseFragment);

        btSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("eneko", String.valueOf(btSaveLocation.isClickable()));
                if(btSaveLocation.isClickable()) {
                    locationChangeListener.onLocationChanged(locationToSave);
                    closeFragment();
                }
            }
        });
        btSaveLocation.setClickable(false);
        ivCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    // Long click to retrieve coordinates and city
                    double latitude = latLng.latitude;
                    double longitude = latLng.longitude;
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    Location location = null;

                    try {
                        //get city name if exists
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (!addresses.isEmpty()) {
                            String cityName = addresses.get(0).getLocality();
                            String coordinates = "City: " + cityName + ", Latitude: " + latitude + ", Longitude: " + longitude;
                            //save selected location
                            Toast.makeText(getContext(), coordinates, Toast.LENGTH_SHORT).show();
                            location = new Location(cityName, latitude, longitude);
                        } else {
                            String coordinates = "Latitude: " + latitude + ", longitude: " + longitude;
                            Toast.makeText(getContext(), coordinates, Toast.LENGTH_SHORT).show();
                            //save selected location
                            location = new Location("nameless", latitude, longitude);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    locationToSave = location;
                    //clear map marks
                    mMap.clear();

                    // Add new mark
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Selected location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    //activate save button
                    btSaveLocation.setClickable(true);
                    int colorCustom = ContextCompat.getColor(getContext(), R.color.activeBackground);
                    ColorStateList colorStateList = ColorStateList.valueOf(colorCustom);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btSaveLocation.setBackgroundTintList(colorStateList);
                    }

                }
            });
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            locationChangeListener = (OnLocationChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLocationChangeListener");
        }
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }


}