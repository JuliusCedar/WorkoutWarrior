package com.example.workoutwarrior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent myIntent = new Intent(this, LoginActivity.class);
        //this.startActivity(myIntent);

        // temp show profile
        ShowProfile(null);
    }

    /*
     * The following functions switch the active fragment
     */
    public void ShowProfile(View view) {
        Fragment profile = new ProfileActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, profile).commit();
    }
    public void ShowEquip(View view) {
        Fragment equipment = new EquipmentActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, equipment).commit();
    }
    public void ShowWorkouts(View view) {
        Fragment workout = new WorkoutActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_layout, workout).commit();
    }


    public void Login(View view) {

        // TODO: Logout ?

        // go back to login
        finish();
    }
}