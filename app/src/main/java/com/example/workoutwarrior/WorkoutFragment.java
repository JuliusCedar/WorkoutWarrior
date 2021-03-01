package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private void setupButton(Button button){
        switch (button.getId()){
            case R.id.dexterity_workout_button:
                button.setOnClickListener(createOnWorkoutClicked(DexterityWorkoutActivity.class));
                setButtonWorkoutTag(button, "dexterity", Profile.getProfile().getdQuest());
                break;
            case R.id.strength_workout_button:
                button.setOnClickListener(createOnWorkoutClicked(StrengthWorkoutActivity.class));
                setButtonWorkoutTag(button, "strength", Profile.getProfile().getsQuest());
                break;
            case R.id.constitution_workout_button:
                button.setOnClickListener(createOnWorkoutClicked(ConstitutionWorkoutActivity.class));
                setButtonWorkoutTag(button, "constitution", Profile.getProfile().getcQuest());
                break;
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

    private void setButtonWorkoutTag(Button button, String workoutType, int level){
        workoutRef.child(workoutType).child(""+level).child("tag").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                button.setText(String.valueOf(task.getResult().getValue()));
            }
        });
    }



}