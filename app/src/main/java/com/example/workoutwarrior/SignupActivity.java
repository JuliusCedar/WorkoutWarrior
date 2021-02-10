package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;
import java.lang.Math;

import androidx.appcompat.app.AppCompatActivity;

/* SignupActivity
 * Manages sign up screen
 */
public class SignupActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.signup);
    }

    public void signup(View v) {
        EditText usernameElement = (EditText) findViewById(R.id.username_login);
        EditText password1Element = (EditText) findViewById(R.id.password_login);
        EditText password2Element = (EditText) findViewById(R.id.password_login_2);
        TextView passwordsDontMatch = (TextView) findViewById(R.id.passwords_no_match);
        TextView noContent = (TextView) findViewById(R.id.no_content);

        String username = usernameElement.getText().toString();
        String password1 = password1Element.getText().toString();
        String password2 = password2Element.getText().toString();

        passwordsDontMatch.setVisibility(View.INVISIBLE);
        noContent.setVisibility(View.INVISIBLE);

        if (username.equals("") || password1.equals("") || password2.equals("")) {
            noContent.setVisibility(View.VISIBLE);
        } else if (password1.equals(password2)) {
            addNewUser(username, password1);
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        } else {
            passwordsDontMatch.setVisibility(View.VISIBLE);
            password1Element.setText("");
            password2Element.setText("");
        }
    }

    /* Upon successfully signing up the players information is added to the db
     */
    public void addNewUser(String username, String password) {
        int id = (int) (Math.random() * 1000000);
        try {
            dbManager.insertIntoProfile(id, username, password, "Knight");
            dbManager.insertIntoStats(1, 1, 1, 1, id);
        } catch (Error e) {
            Log.i("SIGNUP ERROR", e.toString());
        }
    }
}
