package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/* LoginActivity
 * Manages login screen
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    /* login
     * Changes view to profile screen
     * TODO: verify correct login info is given
     */
    public void login(View v){
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }

    /* signup
     * Changes view to signup screen
     */
    public void signup(View v){
        Intent myIntent = new Intent(this, SignupActivity.class);
        this.startActivity(myIntent);
    }
}
