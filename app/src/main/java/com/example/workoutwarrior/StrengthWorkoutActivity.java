package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;

public class StrengthWorkoutActivity extends AppCompatActivity {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference workoutRef = database.getReference().child("quests").child("strength");
    private StrengthWorkoutHelper currentWorkout = new StrengthWorkoutHelper();
//
    private AnimView animView;

    private Anim anim;

    private int [ ] TARGETS = { R.drawable.push_up_down, R.drawable.push_up_mid, R.drawable.push_up_up, R.drawable.push_up_mid };

    private TextView storyView;
    private TextView workoutView;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength_workout);
    //
        storyView = (TextView) findViewById(R.id.story_text);
        workoutView = (TextView) findViewById(R.id.workout_steps);

        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        animView = new AnimView( this, size.x, size.y ,TARGETS );

        Timer animTimer = new Timer( );
        animTimer.schedule( new AnimTimerTask( animView ),
                0, AnimView.DELTA_TIME );

        anim = animView.getAnim( );

    //
        int questLevel = Profile.getProfile().getsQuest();

        workoutRef.child(""+questLevel).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    //Todo: handle failure
                }
                else{
                    currentWorkout = task.getResult().getValue((StrengthWorkoutHelper.class));
                    populateData(questLevel);
                }
            }
        });
    }

    public void quitWorkout(View v){
        finish();
    }

    public void finishWorkout(View v){
        Profile.getProfile().finishSQuest();
        Profile.getProfile().addStrengthExp(currentWorkout.experience);
        Profile.getProfile().saveToDatabase();
        finish();
    }

    private void populateData(int questLevel){
        setStory(questLevel);
        setWorkout();
    }

    private void setStory(int level){
        workoutRef.child(""+level).child("story").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    storyView.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

    private void setWorkout(){
        workoutRef.child("-1").child("Exercises").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    workoutView.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }


}
