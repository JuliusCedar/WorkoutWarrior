package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StrengthWorkoutActivity extends AppCompatActivity {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference workoutRef = database.getReference().child("quests").child("strength");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_workout);


        int questLevel = Profile.getProfile().getsQuest();

        TextView storyView = (TextView)findViewById(R.id.story_text);
        TextView workoutView = (TextView)findViewById(R.id.workout_steps);

        setStory(storyView,questLevel);
        setWorkout(workoutView);

    }

    public void quitWorkout(View v){

        finish();
    }

    public void finishWorkout(View v){
        //database.getReference().child();
        finish();
    }

    private void setStory(TextView v,int level){
        workoutRef.child(""+level).child("story").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                v.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }

    private void setWorkout(TextView v){
        workoutRef.child("-1").child("Exercises").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                v.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }
}
