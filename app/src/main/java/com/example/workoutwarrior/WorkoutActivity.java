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

public class WorkoutActivity extends Fragment{//extends AppCompatActivity {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_workouts, null);
    }


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_workouts);
    }

    / * Open Equipment
     * Changes view to the equipment screen
     * /
    public void openEquipment(){
        Intent myIntent = new Intent(this, EquipmentActivity.class);
        this.startActivity(myIntent);
    }

    / * Open Profile
     * Changes view to the profile screen
     * /
    public void openProfile(){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }*/
}
