package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView playerName = (TextView)findViewById(R.id.name_text);
        TextView playerClass = (TextView)findViewById(R.id.class_display_text);
        TextView playerLevel = (TextView)findViewById(R.id.level_display_text);

        playerName.setText(Profile.getProfile().getName());
        playerClass.setText(Profile.getProfile().getPlayerClass());
        playerLevel.setText(String.valueOf(Profile.getProfile().getLevel()));
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
