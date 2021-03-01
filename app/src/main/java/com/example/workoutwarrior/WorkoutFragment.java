package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WorkoutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        View.OnClickListener buttonClicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.dexterity_workout_button:
                        Intent dexIntent = new Intent(getActivity(), DexterityWorkoutActivity.class);
                        getActivity().startActivity(dexIntent);
                        break;
                    case R.id.strength_workout_button:
                        Intent strIntent = new Intent(getActivity(), StrengthWorkoutActivity.class);
                        getActivity().startActivity(strIntent);
                        break;
                    case R.id.constitution_workout_button:
                        Intent conIntent = new Intent(getActivity(), ConstitutionWorkoutActivity.class);
                        getActivity().startActivity(conIntent);
                        break;
                }
            }
        };

        getActivity().findViewById(R.id.dexterity_workout_button).setOnClickListener(buttonClicked);
        getActivity().findViewById(R.id.strength_workout_button).setOnClickListener(buttonClicked);
        getActivity().findViewById(R.id.constitution_workout_button).setOnClickListener(buttonClicked);
    }
}
