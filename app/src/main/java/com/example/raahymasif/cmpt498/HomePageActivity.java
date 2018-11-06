package com.example.raahymasif.cmpt498;

import android.app.Activity;
import android.view.LayoutInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Map;

import static android.app.PendingIntent.getActivity;



public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextView name_view, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //gets view of header
        View hView = navigationView.getHeaderView(0);
        // Initialize the textview
        name_view = (TextView)hView.findViewById(R.id.headerName);
        email = (TextView)hView.findViewById(R.id.user_email);
        // initlaize the firebase realtime database variables
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseRef = mDatabase.getReference();

        //database listener that sets text of username in the header
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = getIntent().getStringExtra("user_name");
                String x = getIntent().getStringExtra("email");
                //String s = dataSnapshot.child("User").child("karn").child("Fname").getValue(String.class);

                name_view.setText(s);
                if (x != null){
                String decodeEmail = x.replace(",", ".");
                email.setText(decodeEmail);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_draw_open,R.string.navigation_draw_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //this is just for what we should see first at the homescreen when they login
        //we need to later change this to be able to see posts instead of the finding match
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FindMatchFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_findgame);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String s = getIntent().getStringExtra("user_name");

        switch (item.getItemId()){
            case R.id.nav_creategame:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateGameFragment()).commit();
                CreateGameFragment fragment = new CreateGameFragment();
                Bundle arguments = new Bundle();

                arguments.putString("username",s );
                fragment.setArguments(arguments);

                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, fragment, String.valueOf(new CreateGameFragment())).commit();


                break;
            case R.id.nav_findgame:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FindMatchFragment()).commit();
                break;
            case R.id.nav_map:
                //Fragment frg = null;
                //Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.map);
                //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.detach(currentFragment);
                //fragmentTransaction.attach(currentFragment);
                //fragmentTransaction.commit();
                Intent createmap = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(createmap);
                //break;
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Map_Fragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            /*case R.id.nav_send:
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.nav_send:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SendMessageFragment()).commit();
                SendMessageFragment fragmentChat = new SendMessageFragment();
                Bundle argumentsChat = new Bundle();

                argumentsChat.putString("username",s );
                fragmentChat.setArguments(argumentsChat);

                final FragmentTransaction ftChat = getSupportFragmentManager().beginTransaction();
                ftChat.replace(R.id.fragment_container, fragmentChat, String.valueOf(new SendMessageFragment())).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    //since fire base can not handle "." we need to replace it with ","
    //public static String DecodeString(String string) {
      //  return string.replace(",", ".");
    //}
}




