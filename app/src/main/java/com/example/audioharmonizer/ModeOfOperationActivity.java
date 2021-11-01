package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ModeOfOperationActivity extends AppCompatActivity {

    Button automatic_button, manual_button;
    public DrawerLayout drawerLayout_modes_of_operation;
    public ActionBarDrawerToggle actionBarDrawerToggle_modes_of_operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_of_operation);

        automatic_button = (Button)findViewById(R.id.automatic_button);
        manual_button = (Button)findViewById(R.id.manual_button);



        //**********************NavBar Functionality**********************************
        drawerLayout_modes_of_operation = findViewById(R.id.my_drawer_layout_modes_of_operation);
        actionBarDrawerToggle_modes_of_operation = new ActionBarDrawerToggle(this, drawerLayout_modes_of_operation, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_modes_of_operation.addDrawerListener(actionBarDrawerToggle_modes_of_operation);
        actionBarDrawerToggle_modes_of_operation.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //**********************************************************************



        automatic_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ModeOfOperationActivity.this, AutomaticActivity.class);
                startActivity(intent);
            }
        });

        manual_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ModeOfOperationActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle_modes_of_operation.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}