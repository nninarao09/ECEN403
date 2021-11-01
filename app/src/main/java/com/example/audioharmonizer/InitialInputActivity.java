package com.example.audioharmonizer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InitialInputActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout_InitialInputs;
    public ActionBarDrawerToggle actionBarDrawerToggle_InitialInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_input);

        Button start_button = (Button) findViewById(R.id.start_button);


        //**********************NavBar Functionality**********************************
        drawerLayout_InitialInputs = findViewById(R.id.my_drawer_layout_initial_inputs);
        actionBarDrawerToggle_InitialInputs = new ActionBarDrawerToggle(this, drawerLayout_InitialInputs, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_InitialInputs.addDrawerListener(actionBarDrawerToggle_InitialInputs);
        actionBarDrawerToggle_InitialInputs.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //**********************************************************************



        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(InitialInputActivity.this, ModeOfOperationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle_InitialInputs.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}