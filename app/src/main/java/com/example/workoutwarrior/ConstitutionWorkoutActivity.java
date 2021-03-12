package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConstitutionWorkoutActivity extends AppCompatActivity {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference workoutRef = database.getReference().child("quests").child("constitution");

    private ConstitutionWorkoutHelper currentWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constitution_workout);

        workoutRef.child(""+Profile.getProfile().getsQuest()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    //Todo: handle failure
                }
                else{
                    currentWorkout = task.getResult().getValue((ConstitutionWorkoutHelper.class));
                    populateData();
                }
            }
        });
    }

    private void populateData(){
        int questLevel = Profile.getProfile().getsQuest();

        TextView storyView = (TextView)findViewById(R.id.story_text);
        TextView workoutView = (TextView)findViewById(R.id.workout_steps);

        setStory(storyView,questLevel);
        setWorkout(workoutView);
    }

    public void quitWorkout(View v){
        if (Profile.getProfile().completeAchievement("Quiter - You gave up on a quest :p"))
            Toast.makeText(getApplicationContext(), "Completed achievement: Quiter", Toast.LENGTH_SHORT).show();
        Profile.getProfile().saveToDatabase();
        finish();
    }

    public void finishWorkout(View v){
        if (Profile.getProfile().completeAchievement("First steps - You completed a quest!"))
            Toast.makeText(getApplicationContext(), "Completed achievement: First steps", Toast.LENGTH_SHORT).show();
        Profile.getProfile().finishCQuest();
        Profile.getProfile().addConstitutionExp(currentWorkout.experience);
        Profile.getProfile().saveToDatabase();
        finish();
    }

    private void setStory(TextView v,int level){
        workoutRef.child(""+level).child("story").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    v.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void setWorkout(TextView v){
        workoutRef.child("-1").child("Exercises").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    v.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}
