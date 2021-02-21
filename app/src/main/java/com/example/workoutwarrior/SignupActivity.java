package com.example.workoutwarrior;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.lang.Math;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/* SignupActivity
 * Manages sign up screen
 */
public class SignupActivity extends AppCompatActivity {

    private DatabaseManagerDeprecated dbManager;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManagerDeprecated(this);
        mAuth = FirebaseAuth.getInstance();
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
            // return to main activity, TODO: notify did succeed?
        } else {
            passwordsDontMatch.setVisibility(View.VISIBLE);
            password1Element.setText("");
            password2Element.setText("");
        }
    }

    /* Upon successfully signing up the players information is added to the db
     */
    public void addNewUser(String email, String password) {
        Log.d("Create Account: ", email);

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
        EditText name = (EditText) findViewById(R.id.name_signup);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.class_group);
        RadioButton button = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        String email = user.getEmail();
        String characterName = name.getText().toString();
        String characterClass = button.getText().toString();

        Log.i("adding to db: ", email + characterClass + characterName);
    }
}
