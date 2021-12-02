package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class StartSingingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    public int counter = 10;
    public int bpMinute = 0;
    public int bpMeasure = 0;
    public long fullLengthTime = 0;
    Button start_singing_button;
    TextView countDown_textview;
    BluetoothAdapter mBlueAdapter;
    private static final String TAG = "StartSingingActivity";

    public DrawerLayout drawerLayout_StartSinging;
    public ActionBarDrawerToggle actionBarDrawerToggle_StartSinging;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_singing);

        initializeNavBar();

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        bpMinute = Integer.parseInt(globalVariable.getBeatsPerMinute());
        bpMeasure = Integer.parseInt(globalVariable.getBeatsPerMeasure());
        fullLengthTime = (bpMeasure* 60L)/bpMinute;


        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(StartSingingActivity.this, BluetoothActivity.class);
            startActivity(intent);
        }


        start_singing_button = (Button)findViewById(R.id.start_singing_button);
        countDown_textview= (TextView) findViewById(R.id.countDown_textview);

        start_singing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = bpMeasure;
                new CountDownTimer( fullLengthTime*1000, (fullLengthTime*1000/bpMeasure)){
                    public void onTick(long millisUntilFinished){
                        System.out.println("Counter Value: " + String.valueOf(counter));
                        countDown_textview.setText(String.valueOf(counter));
                        counter--;

                    }
                    public void onFinish(){
                        try {
                            countDown_textview.setText("GO!!");
                            Log.d(TAG, "CountDown Timer is working and has finished");
                        }
                        catch (Exception e) {
                            Log.d(TAG, "CountDown Timer is not Working");
                        }
                    }
                }.start();
            }
        });


    }

    public void initializeNavBar(){
        drawerLayout_StartSinging = findViewById(R.id.my_drawer_layout_start_singing);
        actionBarDrawerToggle_StartSinging = new ActionBarDrawerToggle(this, drawerLayout_StartSinging, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_StartSinging.addDrawerListener(actionBarDrawerToggle_StartSinging);
        actionBarDrawerToggle_StartSinging.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_start_singing);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(5).setChecked(true);
        onNavigationItemSelected(menuItem);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle_StartSinging.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(StartSingingActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(StartSingingActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(StartSingingActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(StartSingingActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(StartSingingActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_start_singing) {
            return true;
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(StartSingingActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            Intent intent = new Intent(StartSingingActivity.this, FAQActivity.class);
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