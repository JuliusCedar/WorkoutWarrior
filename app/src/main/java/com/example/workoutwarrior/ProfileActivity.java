package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    /* Open Equipment
     * Changes view to equipment screen
     */
    public void openEquipment(View v){
        Intent myIntent = new Intent(this, EquipmentActivity.class);
        this.startActivity(myIntent);
    }

    /* Open Workouts
     * Changes view to the workouts screen
     */
    public void openWorkouts(View v){
        Intent myIntent = new Intent(this, WorkoutActivity.class);
        this.startActivity(myIntent);
    }

    /* Open Profile
     * Changes view to the profile screen
     */
    public void openProfile(View v){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }

    /* Log Out
     * Changes view to the login screen
     */
    public void logOut(View v){
        Intent myIntent = new Intent(this, LoginActivity.class);
        this.startActivity(myIntent);
    }
}
