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
    private StrengthWorkoutHelper helper = new StrengthWorkoutHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_workout);


        int questLevel = Profile.getProfile().getsQuest();

        workoutRef.child(""+questLevel).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    //Todo: handle failure
                }
                else{
                    helper= (StrengthWorkoutHelper) task.getResult().getValue();
                }
            }
        });

        TextView storyView = (TextView)findViewById(R.id.story_text);
        TextView workoutView = (TextView)findViewById(R.id.workout_steps);

        storyView.setText(helper.story);

        //setStory(storyView,questLevel);
        setWorkout(workoutView);

    }

    public void quitWorkout(View v){
        finish();
    }

    public void finishWorkout(View v){
        Profile.getProfile().finishSQuest();
        Profile.getProfile().addStrengthExp(helper.experience);
        finish();
    }
    /*
    private void setStory(TextView v,int level){
        workoutRef.child(""+level).child("story").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                v.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }
*/
    private void setWorkout(TextView v){
        workoutRef.child("-1").child("Exercises").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                v.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }


}
