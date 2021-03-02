package com.example.workoutwarrior;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView playerName;
    TextView playerClass;
    TextView playerLevel;

    View strBar;
    View dexBar;
    View conBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);

        playerName = (TextView)view.findViewById(R.id.name_text);

        strBar = view.findViewById(R.id.strength_bar);
        dexBar = view.findViewById(R.id.dexterity_bar);
        conBar = view.findViewById(R.id.constitution_bar);

        return view;
    }

    private void setStatBar(View statBar, int statVal){
        statBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                statBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.LayoutParams params = statBar.getLayoutParams();
                params.width = (int)((double)statVal/100.0*(double)statBar.getWidth());
                statBar.setLayoutParams(params);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Profile profile = Profile.getProfile();

        playerName.setText(profile.getName());

        setStatBar(strBar, profile.getStrength());
        setStatBar(dexBar, profile.getDexterity());
        setStatBar(conBar, profile.getConstitution());
    }
}
