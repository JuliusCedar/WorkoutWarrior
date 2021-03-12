package com.example.workoutwarrior;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final int PHOTO_REQUEST = 1;
    private ImageView imageView;
    private Bitmap bitmap;

    TextView playerName;
    TextView playerClass;
    TextView playerLevel;

    View strBar;
    View dexBar;
    View conBar;

    LinearLayout achievements_list_layout;

    ImageView profileImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, null);

        playerName = (TextView) view.findViewById(R.id.name_text);

        strBar = view.findViewById(R.id.strength_bar);
        dexBar = view.findViewById(R.id.dexterity_bar);
        conBar = view.findViewById(R.id.constitution_bar);

        achievements_list_layout = view.findViewById(R.id.achievements_list_layout);

        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(this);

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

        // populate achievements list
        for (String achievement : profile.getAchievements()){
            Button ach = new Button(getContext());
            ach.setText(achievement);

            // temporary? achievement
            ach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Profile.getProfile().completeAchievement("Achieveception - Get an achievement from an achievement"))
                        Toast.makeText(getContext(), "Completed achievement: Achieveception", Toast.LENGTH_SHORT).show();
                    Profile.getProfile().saveToDatabase();
                }
            });

            achievements_list_layout.addView(ach);
        }
    }

    @Override
    public void onClick(View v) {
        Log.i("Profile Image", "Clicked");

        // temporary? achievement
        if (Profile.getProfile().completeAchievement("Who's that? - try to update your image"))
            Toast.makeText(getContext(), "Completed achievement: Who's that?", Toast.LENGTH_SHORT).show();
        Profile.getProfile().saveToDatabase();
    }

}
