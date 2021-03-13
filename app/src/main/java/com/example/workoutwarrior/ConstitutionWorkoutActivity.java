package com.example.workoutwarrior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;

public class ConstitutionWorkoutActivity extends AppCompatActivity {
    //connections to database
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference workoutRef = database.getReference().child("quests").child("constitution");
    private ConstitutionWorkoutHelper currentWorkout;

    //views for animation
    private AnimView animView;
    private Anim anim;
    //pictures for the animations
    private int [ ] TARGETS = { R.drawable.jumping_jack_down, R.drawable.jumping_jack_mid, R.drawable.jumping_jack_up, R.drawable.jumping_jack_mid };
    //connections to xml to change text with database information
    private TextView storyView;
    private TextView workoutView;

    private ViewGroup containerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constitution_workout);


        storyView = (TextView) findViewById(R.id.story_text);
        workoutView = (TextView) findViewById(R.id.workout_steps);

        Point size = new Point( );
        getWindowManager( ).getDefaultDisplay( ).getSize( size );
        animView = new AnimView( this, size.x, size.y ,TARGETS );

        Timer animTimer = new Timer( );
        animTimer.schedule( new AnimTimerTask( animView ),
                0, AnimView.DELTA_TIME );

        anim = animView.getAnim( );


        containerView = (LinearLayout) findViewById(R.id.container);
        containerView.addView(animView);



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

        setStory(questLevel);
        setWorkout();
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

    private void setStory(int level){
        workoutRef.child(""+level).child("story").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
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
