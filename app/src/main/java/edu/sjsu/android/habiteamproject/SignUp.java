package edu.sjsu.android.habiteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private HabiDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        database = new HabiDB(this);

        findViewById(R.id.create_account).setOnClickListener(v -> insertNewAccount());
        findViewById(R.id.back).setOnClickListener(v -> backNav());
    }

    private void backNav(){
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }

    private void insertNewAccount(){
        EditText username = (EditText) findViewById(R.id.new_username);
        EditText password = (EditText) findViewById(R.id.new_password);
        EditText email = (EditText) findViewById(R.id.new_email);

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String mail = email.getText().toString().trim();

        database.insertLogin(user, pass, mail);
    }
}