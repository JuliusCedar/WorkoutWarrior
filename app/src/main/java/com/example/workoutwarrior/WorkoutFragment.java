package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WorkoutFragment extends Fragment {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference workoutRef = database.getReference().child("quests");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, null);
    }

    @Override
    public void onStart() {
        super.onStart();

        setupButton((Button) getActivity().findViewById(R.id.dexterity_workout_button));
        setupButton((Button) getActivity().findViewById(R.id.strength_workout_button));
        setupButton((Button) getActivity().findViewById(R.id.constitution_workout_button));
    }

    private void setupButton(Button button) {
        int id = button.getId();
        if (id == R.id.dexterity_workout_button) {
            button.setOnClickListener(createOnWorkoutClicked(DexterityWorkoutActivity.class));
            setButtonWorkoutTag(button, "dexterity", Profile.getProfile().getdQuest());
        }
        else if (id == R.id.strength_workout_button) {
            button.setOnClickListener(createOnWorkoutClicked(StrengthWorkoutActivity.class));
            setButtonWorkoutTag(button, "strength", Profile.getProfile().getsQuest());
        }
        else if (id == R.id.constitution_workout_button) {
            button.setOnClickListener(createOnWorkoutClicked(ConstitutionWorkoutActivity.class));
            setButtonWorkoutTag(button, "constitution", Profile.getProfile().getcQuest());
        }
    }

    private View.OnClickListener createOnWorkoutClicked(Class activityClass){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), activityClass);
                getActivity().startActivity(myIntent);
            }
        };
    }

    private void setButtonWorkoutTag(Button button, String workoutType, int level) {

        workoutRef.child(workoutType).child(""+level).child("tag").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    // if got result, set text depending on if the quest is completed
                    String name = String.valueOf(task.getResult().getValue());

                    // not finished
                    if(!name.equals("null")) {
                        button.setEnabled(true);
                        button.setText(name);
                    }

                    // finished all
                    else {
                        button.setEnabled(false);
                        button.setText("All completed!");

                        // do achievement
                        if (Profile.getProfile().completeAchievement("OP " + workoutType + " - You maxed out your " + workoutType + "!"))
                            Toast.makeText(getContext(), "Completed achievement: OP " + workoutType, Toast.LENGTH_SHORT).show();

                        // completely done? achievement
                        if (Profile.getProfile().hasCompletedAchievement("OP strength - You maxed out your strength!") &&
                                Profile.getProfile().hasCompletedAchievement("OP constitution - You maxed out your constitution!") &&
                                Profile.getProfile().hasCompletedAchievement("OP dexterity - You maxed out your dexterity!"))
                            if (Profile.getProfile().completeAchievement("Now what? - You completed everything!"))
                                Toast.makeText(getContext(), "Completed achievement: Now what?", Toast.LENGTH_SHORT).show();

                        Profile.getProfile().saveToDatabase();
                    }
                } else{
                    // if failed to get task (client is offline error) TODO: handle
                    getActivity().finish();
                    Toast.makeText(getContext(), "You lost connection, please restart and sign back in", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}