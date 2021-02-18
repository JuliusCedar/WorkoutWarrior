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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);

        TextView playerName = (TextView)view.findViewById(R.id.name_text);
        TextView playerClass = (TextView)view.findViewById(R.id.class_display_text);
        TextView playerLevel = (TextView)view.findViewById(R.id.level_display_text);

        View strBar = view.findViewById(R.id.strength_bar);
        View dexBar = view.findViewById(R.id.dexterity_bar);
        View conBar = view.findViewById(R.id.constitution_bar);

        playerName.setText(Profile.getProfile().getName());
        playerClass.setText(Profile.getProfile().getPlayerClass());
        playerLevel.setText(String.valueOf(Profile.getProfile().getLevel()));

        setStatBar(strBar, Profile.getProfile().getStr());
        setStatBar(dexBar, Profile.getProfile().getDex());
        setStatBar(conBar, Profile.getProfile().getCon());

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

}
