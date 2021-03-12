package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DexterityWorkoutActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    private final static int GPS_LOCATION_CODE = 1;
    private final static int MAP_LOCATION_CODE = 2;
    LocationManager locationManager;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference().child("quests").child("dexterity");

    private DexWorkoutHelper currentWorkout;

    private GoogleMap mMap;

    LatLng currentLocation = null;
    LatLng destination = null;
    LatLng startLocation = null;
    LatLng checkpointLocation = null;

    boolean locationSelected = false;
    boolean reachedDestination = false;
    boolean returnedToStart = false;

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
                    ((TextView) findViewById(R.id.story_text)).setText(currentWorkout.story);
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
                    if(checkInRange(latLng, 50) && !locationSelected){
                        destination = latLng;
                        checkpointLocation = currentLocation;
                        drawMap();
                        findViewById(R.id.input_button).setEnabled(true);
                    }
                }
            });
            drawMap();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        }
        else{
            String[] neededPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, neededPermissions, MAP_LOCATION_CODE);
        }
    }

    private void drawMap(){
        mMap.clear();
        if(!locationSelected){
            mMap.addCircle(new CircleOptions().center(currentLocation).radius(currentWorkout.distance*1000));
        }
        if(destination != null){
            if(!reachedDestination){
                drawRoute(currentLocation, destination);
            }
            else{
                drawRoute(currentLocation, startLocation);
            }
        }
    }

    private void drawRoute(LatLng loc1, LatLng loc2){
        Object[] dataTransfer = new Object[4];
        dataTransfer[0] = mMap;
        dataTransfer[1] = getResources().getString(R.string.google_maps_key);
        dataTransfer[2] = loc1.latitude+","+loc1.longitude;
        dataTransfer[3] = loc2.latitude+","+loc2.longitude;
        mMap.addMarker(new MarkerOptions().position(loc2));
        new GetDirections().execute(dataTransfer);
    }

    public void selectLocation(View view){
        if(destination != null && !locationSelected){
            locationSelected = true;
            startLocation = currentLocation;
            Button inputButton = (Button) findViewById(R.id.input_button);
            TextView message = (TextView)findViewById(R.id.message_text);
            message.setText("Alright! now get to your destination!");
            inputButton.setEnabled(false);
            inputButton.setText("Finish");
            drawMap();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        boolean firstLoc = currentLocation == null;
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if(firstLoc){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            if(mapFragment != null)
                mapFragment.getMapAsync(this);
        }
        if(destination != null){
            if(locationSelected){
                if(compareDistance(currentLocation, destination)<30 && !reachedDestination){
                    reachedDestination = true;
                    ((TextView)findViewById(R.id.message_text)).setText("Great! now make it back to where you started!");
                }
                else if(compareDistance(currentLocation, startLocation)<30 && reachedDestination){
                    ((TextView)findViewById(R.id.message_text)).setText("You made it! Finish the quest to get your reward!");
                    Button inputButton = (Button)findViewById(R.id.input_button);
                    Button returnButton = (Button)findViewById(R.id.finish_button);
                    returnedToStart = true;
                    returnButton.setEnabled(false);
                    inputButton.setEnabled(true);
                    inputButton.setOnClickListener(this::finishWorkout);
                }
            }
            if(compareDistance(currentLocation, checkpointLocation) > 15){
                if(checkInRange(destination, 50) && !locationSelected){
                    destination = null;
                }
                checkpointLocation = currentLocation;
                drawMap();
            }
        }
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

    private boolean checkInRange(LatLng range, int margin){
        return compareDistance(currentLocation, range) > (currentWorkout.distance*1000)-margin && compareDistance(currentLocation, range) < (currentWorkout.distance*1000)+margin;
    }

    public void finishWorkout(View view){
        if (Profile.getProfile().completeAchievement("First steps - You completed a quest!"))
            Toast.makeText(getApplicationContext(), "Completed achievement: First steps", Toast.LENGTH_SHORT).show();
        Profile.getProfile().finishDQuest();
        Profile.getProfile().addDexterityExp(currentWorkout.experience);
        Profile.getProfile().saveToDatabase();
        finish();
    }

    public void goBack(View view){
        if (Profile.getProfile().completeAchievement("Quiter - You gave up on a quest :p"))
            Toast.makeText(getApplicationContext(), "Completed achievement: Quiter", Toast.LENGTH_SHORT).show();
        Profile.getProfile().saveToDatabase();
        finish();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) { }

    @Override
    public void onProviderEnabled(String s) { }

    @Override
    public void onProviderDisabled(String s) { }
}