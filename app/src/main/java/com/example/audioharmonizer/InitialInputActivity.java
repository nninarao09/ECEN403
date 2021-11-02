package com.example.audioharmonizer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InitialInputActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout_InitialInputs, drawerLayout_HomePage;
    public ActionBarDrawerToggle actionBarDrawerToggle_InitialInputs, actionBarDrawerToggle_HomePage;

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

//        @Override
//        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//            NavController navController = Navigation.findNavController(this, R.id.nav_initial_inputs);
//            return NavigationUI.onNavDestinationSelected(item, navController)
//                    || super.onOptionsItemSelected(item);
//        }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//        switch(item.getItemId()){
//
//            case R.id.nav_home:{
//                break;
//            }
//
//            case R.id.nav_initial_inputs:{
//                break;
//            }
//        }
//        item.setChecked(true);
//        drawerLayout_InitialInputs.closeDrawer(GravityCompat.START);
//        return true;
//    }

}