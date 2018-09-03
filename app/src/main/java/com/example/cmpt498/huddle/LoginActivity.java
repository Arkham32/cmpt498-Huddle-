package com.example.cmpt498.huddle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToPassword(View view){

        //grabs the email from the user
        EditText userText = findViewById(R.id.LoginEmail);
        String userEmail = userText.getText().toString();

        //takes the inputs and goes to the password section of the login
        Intent intent = new Intent(this, PasswordActivity.class);
        intent.putExtra("EXTRA_EMAIL",userEmail);
        startActivity(intent);

    }
}
