package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationRestriction;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DexterityWorkoutActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    private final static int GPS_LOCATION_CODE = 1;
    private final static int MAP_LOCATION_CODE = 2;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference().child("quests").child("dexterity");

    private GoogleMap mMap;

    LocationManager locationManager;

    private DexWorkoutHelper currentWorkout;

    LatLng currentLocation = null;
    LatLng destination = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dexterity_workout);

        setupGPS();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dRef.child("" + Profile.getProfile().getdQuest()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    currentWorkout = task.getResult().getValue(DexWorkoutHelper.class);
                    populateData();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupGPS();
                } else {
                    //TODO: Permission denied, handle this
                }
                return;
            case MAP_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupMap();
                } else {
                    //TODO: Permission denied, handle this
                }
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
    }

    private void setupGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean finePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coursePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (finePermission && coursePermission) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } else {
            String[] neededPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, neededPermissions, GPS_LOCATION_CODE);
        }
    }

    private void setupMap(){
        boolean finePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coursePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (finePermission && coursePermission) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if(compareDistance(currentLocation, latLng) > 450 && compareDistance(currentLocation, latLng) < 550){
                        mMap.clear();
                        mMap.addCircle(new CircleOptions().center(currentLocation).radius(500));
                        destination = latLng;
                        mMap.addMarker(new MarkerOptions().position(latLng));
                        drawRoute(currentLocation, destination);
                    }
                }
            });
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            mMap.addCircle(new CircleOptions().center(currentLocation).radius(500));
        }
        else{
            String[] neededPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, neededPermissions, MAP_LOCATION_CODE);
        }
    }


    private void populateData() {
        ((TextView) findViewById(R.id.story_text)).setText(currentWorkout.story);
    }

    private void drawRoute(LatLng loc1, LatLng loc2){
        Object[] dataTransfer = new Object[4];
        dataTransfer[0] = mMap;
        dataTransfer[1] = getResources().getString(R.string.google_maps_key);
        dataTransfer[2] = loc1.latitude+","+loc1.longitude;
        dataTransfer[3] = loc2.latitude+","+loc2.longitude;
        new GetDirections().execute(dataTransfer);
    }

    public void finishWorkout(View view){
        Profile.getProfile().finishDQuest();
        Profile.getProfile().addDexterityExp(currentWorkout.experience);
        Profile.getProfile().saveToDatabase();
        finish();
    }

    public void goBack(View view){
        finish();
    }

    private double compareDistance(LatLng dist1, LatLng dist2){
        Location locationA = new Location("point A");
        locationA.setLatitude(dist1.latitude);
        locationA.setLongitude(dist1.longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(dist2.latitude);
        locationB.setLongitude(dist2.longitude);

        return locationA.distanceTo(locationB);
    }

    @Override
    public void onLocationChanged(Location location) {
        boolean firstLoc = currentLocation == null ? true : false;
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if(firstLoc){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}