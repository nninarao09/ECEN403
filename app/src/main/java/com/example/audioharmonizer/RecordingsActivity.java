package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class RecordingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout_recordings;
    public ActionBarDrawerToggle actionBarDrawerToggle_recordings;
    private NavigationView navigationView;
    private final int PICK_AUDIO = 1;
    Uri AudioUri;
    TextView select_Audio;
    Button play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

        //**********************NavBar Functionality START**********************************
        drawerLayout_recordings = findViewById(R.id.my_drawer_layout_recordings);
        actionBarDrawerToggle_recordings = new ActionBarDrawerToggle(this, drawerLayout_recordings, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_recordings.addDrawerListener(actionBarDrawerToggle_recordings);
        actionBarDrawerToggle_recordings.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_recordings);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(5).setChecked(true);
        onNavigationItemSelected(menuItem);
        //**********************NavBar Functionality END**********************************

        select_Audio = findViewById(R.id.select_Audio);
        select_Audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent audio = new Intent();
                audio.setType("audio/*");
                audio.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(audio, "Select Audio"), PICK_AUDIO);


            }
        });

//        final MediaPlayer mp = audio;
//        try{
//            //you can change the path, here path is external directory(e.g. sdcard) /Music/maine.mp3
//            mp.setDataSource(Environment.getExternalStorageDirectory().getPath()+"/Music/maine.mp3");
//
//            mp.prepare();
//        }catch(Exception e){e.printStackTrace();}
//
//        play_button = findViewById(R.id.play_button);
//        play_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mp.start();
//            }
//        });

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO && resultCode == RESULT_OK) {
            // Audio is Picked in format of URI
            AudioUri = data.getData();
            select_Audio.setText("Audio Selected");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle_recordings.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(RecordingsActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(RecordingsActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(RecordingsActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(RecordingsActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(RecordingsActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_recordings) {
            return true;
        } else if (item.getItemId() == R.id.nav_faq) {
            Intent intent = new Intent(RecordingsActivity.this, FAQActivity.class);
            startActivity(intent);
        } else {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}