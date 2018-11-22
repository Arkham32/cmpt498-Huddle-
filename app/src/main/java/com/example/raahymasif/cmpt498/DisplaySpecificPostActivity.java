package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raahymasif.cmpt498.Model.CreatePosts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Scanner;

public class DisplaySpecificPostActivity extends Activity {

    private DatabaseReference mDatabase;
    String post_key = null;
    String username = null;
    String email = null;
    int playersInt = 0;
    String post_joined, post_info, post_location, post_players, post_postedBy, post_sport, post_date, post_time;


    private TextView sportText;
    private TextView infoText;
    private TextView locationText;
    private TextView playersNeededText;
    private TextView joinedText;
    private TextView postedByText;
    private TextView datetText;
    private TextView timeText;

    Button joinButton, cancelButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayspecificpost);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");
        //mDatabase.keepSynced(true);

        // Initialize the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_post = database.getReference("Posts");

        //gets the post UID
        Bundle extras = getIntent().getExtras();
        post_key = extras.getString("post_key");
        username = extras.getString("username");
        email = extras.getString("email");


        //connecting java variable to xml variable
        sportText = (TextView) findViewById(R.id.sportTextView);
        infoText = (TextView) findViewById(R.id.postInfoTextView);
        locationText = (TextView) findViewById(R.id.locationTextView);
        playersNeededText = (TextView) findViewById(R.id.playersNeededTextView);
        joinedText = (TextView) findViewById(R.id.joinedTextView);
        postedByText = (TextView) findViewById(R.id.postedByTextView);
        datetText = (TextView) findViewById(R.id.dateTextView);
        timeText = (TextView) findViewById(R.id.timeTextView);

        //button
        joinButton = (Button) findViewById(R.id.joinButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post_info = (String) dataSnapshot.child("info").getValue();
                post_location = (String) dataSnapshot.child("location").getValue();
                post_players = (String) dataSnapshot.child("players").getValue();
                post_postedBy = (String) dataSnapshot.child("postedBy").getValue();
                post_sport = (String) dataSnapshot.child("sport").getValue();
                post_joined = (String) dataSnapshot.child("usersJoined").getValue();
                post_date = (String) dataSnapshot.child("date").getValue();
                post_time = (String) dataSnapshot.child("time").getValue();

                //post_players int variable for updating
                playersInt = Integer.parseInt(post_players);


                sportText.setText(post_sport);
                infoText.setText(post_info);
                locationText.setText(post_location);
                playersNeededText.setText(post_players);
                joinedText.setText(post_joined);
                postedByText.setText(post_postedBy);
                datetText.setText(post_date);
                timeText.setText(post_time);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //no code
            }
        });


        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(DisplaySpecificPostActivity.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                if (playersInt == 0) {
                    mDialog.dismiss();
                    Toast.makeText(DisplaySpecificPostActivity.this, "Full Player Capacity..", Toast.LENGTH_SHORT).show();

                    //exits and goes back home
                    Intent homePage = new Intent(DisplaySpecificPostActivity.this, HomePageActivity.class);
                    homePage.putExtra("user_name", username);
                    homePage.putExtra("email",email);
                    startActivity(homePage);
                }

                else {

                    mDialog.dismiss();

                    String info = post_info.toString();
                    String location = post_location.toString();
                    String players = post_players.toString();
                    String postedBy = post_postedBy.toString();
                    String sport = post_sport.toString();
                    String joined = post_joined.toString();
                    String date = post_date.toString();
                    String time = post_time.toString();


                    updateJoined(info,location,players,sport,postedBy,joined,post_key,date,time);

                    //Toast.makeText(DisplaySpecificPostActivity.this, "Joined!", Toast.LENGTH_SHORT).show();


                    Intent homePage = new Intent(DisplaySpecificPostActivity.this,HomePageActivity.class);
                    homePage.putExtra("user_name", username);
                    homePage.putExtra("email",email);
                    startActivity(homePage);

                }

            }
        });

        //cancel will take it back to homepage
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homePage = new Intent(DisplaySpecificPostActivity.this,HomePageActivity.class);
                homePage.putExtra("user_name", username);
                homePage.putExtra("email",email);
                startActivity(homePage);
            }
        });
    }

    private boolean updateJoined(String info, String location, String players, String sport, String postedBy, String usersJoined, String Id, String date, String time){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child(post_key);

        String checkExisting = "false";

        String users[] = usersJoined.split(",");

        for(int i = 0; i<users.length; i++){

            if (users[i].equals(username)) {
                checkExisting = "true";
            }
        }


        if(checkExisting.equals("false")){

            String joined = usersJoined + "," + username;
            int a = Integer.parseInt(players);
            a--;
            players = Integer.toString(a);

            CreatePosts modifyPost = new CreatePosts(info ,location,  players, sport, postedBy, joined, Id, date, time);

            databaseReference.setValue(modifyPost);
            Toast.makeText(DisplaySpecificPostActivity.this, "Joined", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(DisplaySpecificPostActivity.this, "You Have Already Joined", Toast.LENGTH_SHORT).show();

            Intent homePage = new Intent(DisplaySpecificPostActivity.this,HomePageActivity.class);
            homePage.putExtra("user_name", username);
            homePage.putExtra("email",email);
            startActivity(homePage);
        }

        return true;
    }
}
