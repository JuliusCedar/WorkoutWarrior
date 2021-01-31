package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EquipmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_equipment);
    }

    /* Open Profile
     * Changes view to the profile screen
     */
    public void openProfile(){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }

    /* Open Workouts
     * Changes view to the workouts screen
     */
    public void openWorkouts(){
        Intent myIntent = new Intent(this, WorkoutActivity.class);
        this.startActivity(myIntent);
    }
}
