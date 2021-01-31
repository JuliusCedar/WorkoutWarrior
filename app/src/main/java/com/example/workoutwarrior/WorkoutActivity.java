package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_workouts);
    }

    /* Open Equipment
     * Changes view to the equipment screen
     */
    public void openEquipment(){
        Intent myIntent = new Intent(this, EquipmentActivity.class);
        this.startActivity(myIntent);
    }

    /* Open Profile
     * Changes view to the profile screen
     */
    public void openProfile(){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }
}
