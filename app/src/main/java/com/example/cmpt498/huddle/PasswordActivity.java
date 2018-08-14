package com.example.cmpt498.huddle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();

        // gets the user email
        String user = intent.getStringExtra("EXTRA_EMAIL");

        //-------------------------------TEST------------------------------
        //displays to the textView called displayUser
        TextView textView = findViewById(R.id.displayUser);
        textView.setText(user);
        //-----------------------------------------------------------------

    }
}
