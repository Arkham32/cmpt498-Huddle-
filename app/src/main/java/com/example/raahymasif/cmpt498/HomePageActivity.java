package com.example.raahymasif.cmpt498;

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
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        switch (item.getItemId()){
            case R.id.nav_creategame:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateGameFragment()).commit();
                break;
            case R.id.nav_findgame:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FindMatchFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
            /*case R.id.nav_send:
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.nav_send:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SendMessageFragment()).commit();
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

}
