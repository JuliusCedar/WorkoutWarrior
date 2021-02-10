package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class EquipmentActivity extends Fragment{/*AppCompatActivity {
 */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_equipment, null);
    }
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_equipment);
    }

    / * Open Profile
     * Changes view to the profile screen
     * /
    public void openProfile(){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }

    / * Open Workouts
     * Changes view to the workouts screen
     * /
    public void openWorkouts(){
        Intent myIntent = new Intent(this, WorkoutActivity.class);
        this.startActivity(myIntent);
    } */
}
