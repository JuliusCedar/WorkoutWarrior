package com.example.workoutwarrior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);

        TextView playerName = (TextView)view.findViewById(R.id.name_text);
        TextView playerClass = (TextView)view.findViewById(R.id.class_display_text);
        TextView playerLevel = (TextView)view.findViewById(R.id.level_display_text);

        playerName.setText(Profile.getProfile().getName());
        playerClass.setText(Profile.getProfile().getPlayerClass());
        playerLevel.setText(String.valueOf(Profile.getProfile().getLevel()));

        return view;
    }
}
