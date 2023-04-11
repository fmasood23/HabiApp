package edu.sjsu.android.habiteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.user);
        passwordInput = findViewById(R.id.pass);
        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.signup);

        loginButton.setOnClickListener(v -> handleLogin());
        signUpButton.setOnClickListener(v -> startSignUp());
    }


    private void login(String userName, String password) {
        //TODO: validate username and passwords with DB
            if ( password.equals(getPass(userName))) {
                Toast.makeText(this, "good", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, HomePage.class);
                startActivity(i);
            } else {
                Toast.makeText(this, getPass(userName), Toast.LENGTH_SHORT).show();
            }
    }

    private void handleLogin(){
        String user = usernameInput.getText().toString();
        String pass = passwordInput.getText().toString();

        //validation if empty
        if (user.isEmpty() && pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "both vals are empty", Toast.LENGTH_SHORT).show();
        } else if (user.isEmpty()) {
            Toast.makeText(MainActivity.this, "username empty", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "password empty", Toast.LENGTH_SHORT).show();
        } else {
            login(user, pass);
        }
    }

    private void startSignUp(){
        Intent i = new Intent(MainActivity.this, SignUp.class);
        startActivity(i);
    }

    public String getPass(String user) {
        // Sort by student name
        try (Cursor c = getContentResolver().
                query(HabiProvider.CONTENT_URI, null, user, null, null)) {
            if (c.moveToFirst()) {
                String result = "";
                do {
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        result = result.concat
                                (c.getString(i));
                    }
                } while (c.moveToNext());
                return result;
            }
            else{
                return null;
            }
        }

    }
}