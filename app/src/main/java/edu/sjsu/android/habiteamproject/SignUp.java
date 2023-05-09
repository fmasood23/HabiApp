package edu.sjsu.android.habiteamproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        findViewById(R.id.create_account).setOnClickListener(v -> insertNewAccount());
        findViewById(R.id.back).setOnClickListener(v -> backNav());
    }

    private void backNav(){
        Intent i = new Intent(SignUp.this, MainActivity.class);
        startActivity(i);
    }

    private void insertNewAccount(){
        EditText username = findViewById(R.id.new_username);
        EditText password = findViewById(R.id.new_password);
        EditText email = findViewById(R.id.new_email);

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String mail = email.getText().toString().trim();

        if(user.isEmpty() || pass.isEmpty() || mail.isEmpty()){
            Toast.makeText(SignUp.this, "Fields are still left empty", Toast.LENGTH_SHORT).show();
        }
        else {
            try{
                String mail_regex = getString(R.string.regex);
                Pattern p = Pattern.compile(mail_regex);
                Matcher m = p.matcher(mail);
                if(!m.matches()){
                    Toast.makeText(SignUp.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", user);
                contentValues.put("password", pass);
                contentValues.put("email", mail);
                getContentResolver().insert(HabiProvider.CONTENT_URI, contentValues);

                ContentValues contentValues1 = new ContentValues();
                contentValues1.put("username", user);
                contentValues1.put("logged_in", "false");
                getContentResolver().insert(HabiProvider.CONTENT_URI_CURRENT, contentValues1);

                Toast.makeText(SignUp.this, "Account Created", Toast.LENGTH_SHORT).show();
            }
            catch(SQLiteConstraintException e){
                Toast.makeText(SignUp.this, "Account already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }
}