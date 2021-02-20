package com.example.workoutwarrior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // temp show profile, TODO: it should be different if ^ fails
        ShowProfile(null);
    }

    /*
     * The following functions switch the active fragment
     */
    public void ShowProfile(View view) {
        Fragment profile = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, profile).commit();
    }
    public void ShowEquip(View view) {
        Fragment equipment = new EquipmentFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, equipment).commit();
    }
    public void ShowWorkouts(View view) {
        Fragment workout = new WorkoutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, workout).commit();
    }

    // takes the user back to the login activity
    public void Logout(View view) {
        // Shows login interface
        finish();
    }
}