package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import java.util.Dictionary;


/* LoginActivity
 * Manages login screen
 */
public class LoginActivity extends AppCompatActivity {
    private DatabaseManagerDeprecated dbManager;

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference dRef = database.getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManagerDeprecated(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login);

        // remember me feature
        String remembered_email = getSharedPreferences("Login_prefs", MODE_PRIVATE).getString("remembered_email", null);
        if(remembered_email != null) {
            ((EditText) findViewById(R.id.username_login)).setText(remembered_email);
            ((CheckBox)findViewById(R.id.remember_me)).setChecked(true);
        }
    }


    /* login
     * Changes view to profile screen and verifies that correct login information is given.
     */
    public void login(View v){
        EditText usernameElement = (EditText) findViewById(R.id.username_login);
        EditText passwordElement = (EditText)  findViewById(R.id.password_login);
        TextView badLogin = (TextView) findViewById(R.id.bad_login);

        String email = usernameElement.getText().toString();
        String password = passwordElement.getText().toString();

        // if either input was empty
        if (email.isEmpty() || password.isEmpty()) {
            badLogin.setVisibility(View.VISIBLE);
        }

        else {
            badLogin.setVisibility(View.INVISIBLE);
            signIn(email, password);
        }
    }

    /* signIn
     * Signs a user in if they give valid login information
     */
    public void signIn(String email, String password) {
        Log.i("Sign In: ", email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("Sign In: ", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loadProfile(user);
                        } else {
                            Log.w("Sign In: ", "failed", task.getException());
                            loadProfile(null);
                        }
                    }
                });
    }

    /* loadProfile
     * Loads the profile of the user that has signed in.
     */
    public void loadProfile(FirebaseUser user) {
        TextView badLogin = (TextView) findViewById(R.id.bad_login);

        if (user != null) {
            Log.i("Load Profile: ", user.getEmail());
            badLogin.setVisibility(View.INVISIBLE);
            Task<DataSnapshot> profile = dRef.child("profiles")
                    .child(user.getUid())
                    .get();

            profile.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    Log.i("onComplete", "onComplete invoked...");

                    if(!task.isSuccessful() && task.getException().getMessage().equals("Client is offline")){
                        // if failed to get task (client is offline error) TODO: handle
                        Toast.makeText(getApplicationContext(), "You lost connection, please restart and sign back in", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // reset views, and update remember me feature
                        if( ((CheckBox)findViewById(R.id.remember_me)).isChecked() )
                            getSharedPreferences("Login_prefs", MODE_PRIVATE).edit().putString("remembered_email", user.getEmail()).apply();
                        else {
                            getSharedPreferences("Login_prefs", MODE_PRIVATE).edit().remove("remembered_email").apply();
                            ((EditText) findViewById(R.id.username_login)).setText("");
                        }
                        ((EditText)  findViewById(R.id.password_login)).setText("");

                        // setup profile and go to main activity
                        Profile.getProfile().setData(task.getResult().getValue(ProfileData.class));
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
