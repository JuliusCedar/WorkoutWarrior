package com.example.workoutwarrior;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/* SignupActivity
 * Manages sign up screen
 */
public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        setContentView(R.layout.signup);
    }

    public void signup(View v) {
        EditText emailElement = (EditText) findViewById(R.id.email_login);
        EditText password1Element = (EditText) findViewById(R.id.password_login);
        EditText password2Element = (EditText) findViewById(R.id.password_login_2);
        TextView passwordsDontMatch = (TextView) findViewById(R.id.passwords_no_match);
        TextView noContent = (TextView) findViewById(R.id.no_content);
        EditText name = (EditText) findViewById(R.id.name_signup);

        String characterName = name.getText().toString();
        String email = emailElement.getText().toString();
        String password1 = password1Element.getText().toString();
        String password2 = password2Element.getText().toString();

        passwordsDontMatch.setVisibility(View.INVISIBLE);
        noContent.setVisibility(View.INVISIBLE);

        if (email.equals("") || characterName.equals("") || password1.equals("") || password2.equals("")) {
            noContent.setVisibility(View.VISIBLE);
        } else if (password1.equals(password2)) {
            addNewUser(email, password1);
        } else {
            passwordsDontMatch.setVisibility(View.VISIBLE);
            password1Element.setText("");
            password2Element.setText("");
        }
    }

    /* Upon successfully signing up the players information is added to the db
     */
    public void addNewUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Signup Status: ", "Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            addToDb(user);
                            finish();
                        } else {
                            Log.w("Signup Status: ", "Failed", task.getException());
                        }
                    }
                });
    }

    /* addToDb
     * Adds a new profile into the database
     */
    public void addToDb(FirebaseUser user) {
        DatabaseReference profilesRef = db.getReference("profiles");

        EditText name = (EditText) findViewById(R.id.name_signup);
        String characterName = name.getText().toString();
        
        profilesRef.child(user.getUid()).setValue(new ProfileData(characterName));
    }
}
