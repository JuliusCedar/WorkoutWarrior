package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Dictionary;

/* LoginActivity
 * Manages login screen
 */
public class LoginActivity extends AppCompatActivity {
    private DatabaseManagerDeprecated dbManager;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManagerDeprecated(this);
        setContentView(R.layout.login);
    }

    /* login
     * Changes view to profile screen and verifies that correct login information is given.
     */
    public void login(View v){
        EditText usernameElement = (EditText) findViewById(R.id.username_login);
        EditText passwordElement = (EditText)  findViewById(R.id.password_login);
        TextView badLogin = (TextView) findViewById(R.id.bad_login);

        String username = usernameElement.getText().toString();
        String password = passwordElement.getText().toString();

        int playerId = dbManager.getPlayerId(username, password);

        if (playerId != -1) {
            dRef.child("profiles").child("Alex").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(!task.isSuccessful()){
                        //Todo: handle failure
                    }
                    else{
                        Profile.loadProfile(task.getResult().getValue(Profile.class));
                        launchApp();
                    }
                }
            });
        } else {
            badLogin.setVisibility(View.VISIBLE);
        }

    }

    /* signup
     * Changes view to signup screen
     */
    public void signup(View v){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }

    private void launchApp(){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }
}
