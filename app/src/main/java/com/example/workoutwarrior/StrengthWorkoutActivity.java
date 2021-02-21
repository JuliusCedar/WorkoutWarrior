package com.example.workoutwarrior;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StrengthWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_workout);

        int questLevel = Profile.getProfile().getsQuest();

        TextView storyView = (TextView)findViewById(R.id.story_text);



    }

    public void quitWorkout(View v){
        finish();
    }

    public void finishWorkout(View v){
        finish();
    }
}