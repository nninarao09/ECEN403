package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // define the global variable
    TextView batteryLevel_tv;
    Button next_button;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    BluetoothAdapter mBlueAdapter;

    private ReadInput mReadThread = null;
    int batteryLevel = 0;
    ProgressBar progress;

    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    //BluetoothSocket mBTSocket = preferences.getBluetoothSocket("mBTSocket", null);

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();


        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(HomePageActivity.this, Bluetooth2Activity.class);
            startActivity(intent);
        }

        //**********************NavBar Functionality**********************************
        drawerLayout = findViewById(R.id.my_drawer_layout_home_page);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_home);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(menuItem);
        //**********************************************************************

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the both Button and textview

        //globalVariable.getmBluetoothConnection().getConnectedThread();
        mReadThread = new ReadInput(globalVariable.getmBluetoothConnection().getSocket());



        next_button = (Button)findViewById(R.id.get_started);
        next_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                mReadThread.stop();
                Intent intent = new Intent(HomePageActivity.this, InitialInputActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            return true;
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_start_singing) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, StartSingingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            mReadThread.stop();
            Intent intent = new Intent(HomePageActivity.this, FAQActivity.class);
            startActivity(intent);
        } else {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private class ReadInput implements Runnable {

        private Thread t;
        private BluetoothSocket mBTSocket;
        private Boolean runningThread = true;



        public ReadInput(BluetoothSocket mSocket) {
            t = new Thread(this, "Input Thread");
            t.start();
            mBTSocket = mSocket;
        }

        public boolean isRunning() {
            return t.isAlive();
        }

        @Override
        public void run() {
            InputStream inputStream;

            try {
                inputStream = mBTSocket.getInputStream();
                byte[] buffer = new byte[1024];
                int bytes;
                int[] BL = {0, 0, 0};

                int count = 0;

                while (runningThread) {

                    bytes = inputStream.read(buffer);
                    final String strInput = new String(buffer, 0, bytes);
                    System.out.println("BATTERY LEVEL: " + strInput);

                    if(!strInput.equals("d")){
                        BL[count] = Integer.parseInt(strInput);
                        count++;

                    } else{

                        batteryLevel = 100*BL[0] + 10*BL[1] + BL[2];
                        progress = (ProgressBar) findViewById(R.id.simpleProgressBar);



                        if(batteryLevel <= 100){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(batteryLevel < 21){
                                        progress.setProgressTintList(ColorStateList.valueOf(Color.RED));
                                    } else{
                                        progress.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                    }

                                    progress.setProgress(batteryLevel);
                                }
                            });
                        }

                        count = 0;
                    }

                    //}
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void stop() {
            runningThread = false;
//            try {
//                //mBTSocket.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                //System.out.println("ERROR CLOSING THREAD");
//
//            }
        }

    }
}

