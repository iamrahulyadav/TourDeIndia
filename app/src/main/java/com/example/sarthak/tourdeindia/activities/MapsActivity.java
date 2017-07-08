package com.example.sarthak.tourdeindia.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.model.CityMap;
import com.example.sarthak.tourdeindia.utils.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private double cityLatitude = 0.0;
    private double cityLongitude = 0.0;

    int cityIndex, position;
    String[] famousPlacesArray;
    String famousPlaceName;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolBar);
        setSupportActionBar(mToolbar);

        // access city index and cardview position from FamousPlacesFragment
        cityIndex = getIntent().getIntExtra(Constants.KEY_CITY_INDEX, -1);
        position = getIntent().getIntExtra(Constants.KEY_CARD_POSITION, -1);

        // retrieve famous place name from 'array.xml' using city index and position
        famousPlacesArray = getResources().getStringArray(R.array.famousPlaces);
        famousPlaceName = famousPlacesArray[cityIndex + (position * 8)];

        // transform famous place name to geo-coordinates
        // city name is added to improve accuracy
        getLocationFromAddress(famousPlaceName + ", " + new CityMap().getCityName(cityIndex));

        // obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // set up toolbar
        setUpToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // launch animation
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near the city to be accessed.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // add a marker in city to be accessed and move the camera
        LatLng cityLatLng = new LatLng(cityLatitude, cityLongitude);
        mMap.addMarker(new MarkerOptions().position(cityLatLng).title(famousPlaceName));
        float zoomLevel = (float) 15.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLatLng, zoomLevel));
    }

    /**
     * Set up activity toolbar
     */
    private void setUpToolbar() {

        if (getSupportActionBar() != null) {

            // set toolbar title as city name.
            getSupportActionBar().setTitle(famousPlaceName);
        }
    }

    /**
     * Performs geocoding, returning the coordinates of the place
     * whose coordinates are to be accessed.
     *
     * @param strAddress is name of the place whose coordinates need to be accessed
     */
    public void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Address location = null;

        try {
            // returns an array of addresses that are known to describe the named location
            address = coder.getFromLocationName(strAddress, 1);

            if (address != null) {
                location = address.get(0);
                location.getLatitude();
                location.getLongitude();
            }

            if (location != null) {
                cityLatitude = location.getLatitude();
                cityLongitude = location.getLongitude();
            }
        } catch (IOException e) {

            cityLatitude = 0.0;
            cityLongitude = 0.0;
        }
    }
}
