package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;

public class ModeOfOperationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button automatic_button, manual_button;
    public DrawerLayout drawerLayout_modes_of_operation;
    public ActionBarDrawerToggle actionBarDrawerToggle_modes_of_operation;
    private NavigationView navigationView;
    BluetoothAdapter mBlueAdapter;

    private ReadInput mReadThread = null;
    int batteryLevel = 0;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_of_operation);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        automatic_button = (Button)findViewById(R.id.automatic_button);
        manual_button = (Button)findViewById(R.id.manual_button);

        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(ModeOfOperationActivity.this, Bluetooth2Activity.class);
            startActivity(intent);
        }

        //**********************NavBar Functionality**********************************
        drawerLayout_modes_of_operation = findViewById(R.id.my_drawer_layout_modes_of_operation);
        actionBarDrawerToggle_modes_of_operation = new ActionBarDrawerToggle(this, drawerLayout_modes_of_operation, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_modes_of_operation.addDrawerListener(actionBarDrawerToggle_modes_of_operation);
        actionBarDrawerToggle_modes_of_operation.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_modes);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(2).setChecked(true);
        onNavigationItemSelected(menuItem);
        //**********************************************************************


        mReadThread = new ReadInput(globalVariable.getmBluetoothConnection().getSocket());

        automatic_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                globalVariable.getInitialInputsArray()[3] = "Automatic";
                mReadThread.stop();
                Intent intent = new Intent(ModeOfOperationActivity.this, AutomaticActivity.class);
                startActivity(intent);
            }
        });

        manual_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                globalVariable.getInitialInputsArray()[3] = "Manual";

//                for (String i : globalVariable.getManualArrayList()) {
//                    showToast(i);
//                }
                mReadThread.stop();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            return true;
        }else if (item.getItemId() == R.id.nav_automatic) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_start_singing) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, StartSingingActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_faq) {
            mReadThread.stop();
            Intent intent = new Intent(ModeOfOperationActivity.this, FAQActivity.class);
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
                    System.out.println("BATTERY LEVEL mode of operation: " + strInput);

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
        }

    }
}