package com.example.audioharmonizer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class InitialInputActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout_InitialInputs;
    public ActionBarDrawerToggle actionBarDrawerToggle_InitialInputs;
    private NavigationView navigationView;
    BluetoothAdapter mBlueAdapter;


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


        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(InitialInputActivity.this, Bluetooth2Activity.class);
            startActivity(intent);
        }


        //**********************NavBar Functionality**********************************
        drawerLayout_InitialInputs = findViewById(R.id.my_drawer_layout_initial_inputs);
        actionBarDrawerToggle_InitialInputs = new ActionBarDrawerToggle(this, drawerLayout_InitialInputs, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_InitialInputs.addDrawerListener(actionBarDrawerToggle_InitialInputs);
        actionBarDrawerToggle_InitialInputs.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_initial_inputs);
        navigationView.setNavigationItemSelectedListener(this);

        //the integer determines which page on the navbar is highlighted
        MenuItem menuItem = navigationView.getMenu().getItem(1).setChecked(true);
        onNavigationItemSelected(menuItem);

        //**********************************************************************



        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //Save inputs in global automatic array to access in other files

                globalVariable.setNameOfSong(name_of_song.getText().toString());
                globalVariable.setBeatsPerMeasure(beats_per_measure.getText().toString());
                globalVariable.setBeatsPerMinute(beats_per_minute.getText().toString());

                globalVariable.getInitialInputsArray()[0] =  globalVariable.getNameOfSong();
                globalVariable.getInitialInputsArray()[1] = globalVariable.getBeatsPerMeasure();
                globalVariable.getInitialInputsArray()[2] = globalVariable.getBeatsPerMinute();


                //here should be an error checker -> if fields are empty then it should not proceed to the next page
                //this does not work
//                if (beats_per_measure.getText().toString().trim().length() > 0){
//                    showToast("You must enter in all the fields to continue");
//                } else{
                    Intent intent = new Intent(InitialInputActivity.this, ModeOfOperationActivity.class);
                    startActivity(intent);
                //}

//                String Nina = "THE APP IS ACTIVELY SENDING DATA";
//                byte[] bytes = Nina.getBytes(Charset.defaultCharset());
//                globalVariable.getmBluetoothConnection().write(bytes);



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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(InitialInputActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            return true;
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(InitialInputActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(InitialInputActivity.this, AutomaticActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(InitialInputActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_start_singing) {
            Intent intent = new Intent(InitialInputActivity.this, StartSingingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(InitialInputActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            Intent intent = new Intent(InitialInputActivity.this, FAQActivity.class);
            startActivity(intent);
        } else {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}