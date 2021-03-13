package com.example.workoutwarrior;

import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final int PHOTO_REQUEST = 1;
    private Bitmap bitmap;

    TextView playerName;
    TextView playerClass;
    TextView playerLevel;

    View strBar;
    View dexBar;
    View conBar;

    LinearLayout achievements_list_layout;

    ImageView profileImage;

    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

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

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, PHOTO_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(bitmap);
            saveProfilePhoto(bitmap);
        } else {
            Log.i("Fail", "photo");
        }
    }

    public void saveProfilePhoto(Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://workoutwarrior.appspot.com");

        FirebaseUser user = mAuth.getCurrentUser();
        StorageReference testRef = storageRef.child(user.getUid());

        ByteArrayOutputStream image = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, image);
        byte[] data = image.toByteArray();

        UploadTask uploadTask = testRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Add Image To Firebase", "Failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i("Add Image To Firebase", "Success");
            }
        });

    }

}
