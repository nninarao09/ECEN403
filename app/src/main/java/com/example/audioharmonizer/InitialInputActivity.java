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
import android.widget.EditText;
import android.widget.Toast;

public class InitialInputActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout_InitialInputs, drawerLayout_HomePage;
    public ActionBarDrawerToggle actionBarDrawerToggle_InitialInputs, actionBarDrawerToggle_HomePage;
    public String NameOfSong;
    //public String BeatsPerMeasure;
    //public String BeatsPerMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_input);

        //accessing global variables
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        Button start_button = (Button) findViewById(R.id.start_button);
        EditText name_of_song = (EditText) findViewById(R.id.name_of_song);
        EditText beats_per_measure = (EditText) findViewById(R.id.beats_per_measure);
        EditText beats_per_minute = (EditText) findViewById(R.id.beats_per_minute);



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
                //Save inputs in global variable to access in other files
                //String test  = setNameOfSong(name_of_song.getText().toString());
                //BeatsPerMeasure = beats_per_measure.getText().toString();
                //BeatsPerMinute = beats_per_minute.getText().toString();

                //sets Name of the song to what is in the edit text field
                globalVariable.setNameOfSong(name_of_song.getText().toString());
                //globalVariable.setBeatsPerMeasure(beats_per_measure.getText().toString());
                //showToast(globalVariable.getNameOfSong());
                //save above values to global array

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

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}