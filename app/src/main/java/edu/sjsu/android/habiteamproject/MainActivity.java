package edu.sjsu.android.habiteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = usernameInput.getText().toString();
                String pass = passwordInput.getText().toString();

                //validation if empty
                if (TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "both vals are empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(user)) {
                    Toast.makeText(MainActivity.this, "username empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "password empty", Toast.LENGTH_SHORT).show();
                }

                login(user, pass);
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });
    }


    private void login(String userName, String password) {
        //TODO: validate username and passwords with DB
            if (userName.equals("admin") && password.equals("admin")) {
                Toast.makeText(this, "good", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, HomePage.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "bad", Toast.LENGTH_SHORT).show();
            }
    }
}