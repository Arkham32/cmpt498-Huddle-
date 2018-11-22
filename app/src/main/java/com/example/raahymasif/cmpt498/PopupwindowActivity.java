package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopupwindowActivity extends Activity {


    Button Join, Cancel;
    TextView info, sport, distance1;
    private DatabaseReference mDatabase;
    String post_info, post_sport, post_distance;
    String post_key, username, email, distance;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_post = database.getReference("Posts");

        Join = (Button)findViewById(R.id.joinbuttonevent);
        Cancel = (Button)findViewById(R.id.cancelbuttonevent);
        info = (TextView)findViewById(R.id.eventinfo);
        sport = (TextView)findViewById(R.id.eventsport);
        distance1 = (TextView)findViewById(R.id.distance);
        Bundle extras = getIntent().getExtras();
        post_key = extras.getString("post_key");
        username = extras.getString("username");
        email = extras.getString("email");
        distance = extras.getString("distance");

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post_info = (String) dataSnapshot.child("info").getValue();
                post_sport = (String) dataSnapshot.child("sport").getValue();
                post_distance = (String)distance;

                //post_players int variable for updating


                sport.setText(post_sport);
                info.setText(post_info);
                distance1.setText(post_distance);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newmarker = new Intent(PopupwindowActivity.this, DisplaySpecificPostActivity.class);
                newmarker.putExtra("post_key", post_key);
                newmarker.putExtra("username", username);
                newmarker.putExtra("email", email);
                startActivity(newmarker);
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newmarker = new Intent(PopupwindowActivity.this, HomePageActivity.class);
                newmarker.putExtra("post_key", post_key);
                newmarker.putExtra("username", username);
                newmarker.putExtra("email", email);
                startActivity(newmarker);
            }
        });

    }
}

