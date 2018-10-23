package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DrawerData extends Activity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextView name_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        // Initialize the view
         name_view = (TextView)findViewById(R.id.headerName);

         // this is in the onCreate method
        // initlaize the firebase realtime database variables
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        //databse listener
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.child("User").child("karn").child("Fname").getValue(String.class);

                name_view.setText("Hello: "+ s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                setTextView(databaseError.toString());
            }
        });

    }

    private void setTextView (String s)
    {
        name_view.setText("Hello: " + s);
    }

}
