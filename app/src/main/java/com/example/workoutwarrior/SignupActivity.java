package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/* SignupActivity
 * Manages sign up screen
 */
public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    /* signup
     * creates a new account given some login information
     * TODO: Implement a sign up system
     */
    public void signup(View v){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }
}
