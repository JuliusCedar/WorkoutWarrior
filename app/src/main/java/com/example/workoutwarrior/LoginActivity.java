package com.example.workoutwarrior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

/* LoginActivity
 * Manages login screen
 */
public class LoginActivity extends AppCompatActivity {
    private DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
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
        String enteredPassword = passwordElement.getText().toString();

        String password = dbManager.selectPlayerByName(username);

        if (password.equals(enteredPassword)) {
            Intent myIntent = new Intent(this, ProfileActivity.class);
            this.startActivity(myIntent);
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
}
