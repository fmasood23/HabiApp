package edu.sjsu.android.habiteamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        backButton = findViewById(R.id.back);

        backButton.setOnClickListener(v -> backNav());
    }

    private void backNav(){
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }
}