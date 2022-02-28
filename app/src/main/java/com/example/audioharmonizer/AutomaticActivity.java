package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutomaticActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button automatic_finish_button;//send button

    private static final String TAG = "AutomaticActivity";

    BluetoothDevice mBTDevice;
    BluetoothAdapter mBlueAdapter;
    //public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    public DrawerLayout drawerLayout_automatic;
    public ActionBarDrawerToggle actionBarDrawerToggle_automatic;
    private NavigationView navigationView;
    public String delim = ";";

    private ReadInput mReadThread = null;
    int batteryLevel = 0;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        //**********************************bluetooth***************************************


        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(AutomaticActivity.this, Bluetooth2Activity.class);
            startActivity(intent);
        }


        //**********************************bluetooth***************************************



        //**********************NavBar Functionality START**********************************
        drawerLayout_automatic = findViewById(R.id.my_drawer_layout_automatic);
        actionBarDrawerToggle_automatic = new ActionBarDrawerToggle(this, drawerLayout_automatic, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_automatic.addDrawerListener(actionBarDrawerToggle_automatic);
        actionBarDrawerToggle_automatic.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_automatic);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(3).setChecked(true);
        onNavigationItemSelected(menuItem);
        //**********************NavBar Functionality END**********************************


        Spinner cp_spinner = (Spinner) findViewById(R.id.cp_spinner);
        Spinner cp_spinner2 = (Spinner) findViewById(R.id.cp_spinner2);
        Spinner cp_spinner3 = (Spinner) findViewById(R.id.cp_spinner3);
        Spinner cp_spinner4 = (Spinner) findViewById(R.id.cp_spinner4);

        Spinner noh_spinner = (Spinner) findViewById(R.id.noh_spinner);


        //***************************Chord Progression Spinners***************************************

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chord_progressions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cp_spinner.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chord_progressions));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cp_spinner2.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chord_progressions));
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cp_spinner3.setAdapter(myAdapter3);

        ArrayAdapter<String> myAdapter4 = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chord_progressions));
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cp_spinner4.setAdapter(myAdapter4);
        //*********************************************************************************************************

        ArrayAdapter<String> myHarmonyAdapter = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.number_of_harmonies));
        myHarmonyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noh_spinner.setAdapter(myHarmonyAdapter);

        mReadThread = new ReadInput(globalVariable.getmBluetoothConnection().getSocket());


        automatic_finish_button = (Button)findViewById(R.id.automatic_finish_button);
        automatic_finish_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //save spinner inputs
                String chord_spinner = cp_spinner.getSelectedItem().toString();
                String chord_spinner2 = cp_spinner2.getSelectedItem().toString();
                String chord_spinner3 = cp_spinner3.getSelectedItem().toString();
                String chord_spinner4 = cp_spinner4.getSelectedItem().toString();
                String harmony_spinner = noh_spinner.getSelectedItem().toString();


                globalVariable.getAutomaticArray()[0] = harmony_spinner;
                globalVariable.getAutomaticArray()[1] = chord_spinner;
                globalVariable.getAutomaticArray()[2] = chord_spinner2;
                globalVariable.getAutomaticArray()[3] = chord_spinner3;
                globalVariable.getAutomaticArray()[4] = chord_spinner4;



                //Writing data to the other device
                for(int i=0; i<4; ++i){
                    System.out.println("Automatic Output: " + globalVariable.getInitialInputsArray()[i]);
                    globalVariable.getmBluetoothConnection().write(globalVariable.getInitialInputsArray()[i].getBytes(Charset.defaultCharset()));
                    globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));

                }
                for(int i=0; i<5; ++i){
                    System.out.println("Automatic Output: " + globalVariable.getAutomaticArray()[i]);
                    globalVariable.getmBluetoothConnection().write(globalVariable.getAutomaticArray()[i].getBytes(Charset.defaultCharset()));
                    globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));
                }

                mReadThread.stop();
                Intent intent = new Intent(AutomaticActivity.this, StartSingingActivity.class);
                startActivity(intent);
            }
        });





    }


//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        //first cancel discovery because its very memory intensive.
//        mBluetoothAdapter.cancelDiscovery();
//
//        Log.d(TAG, "onItemClick: You Clicked on a device.");
//        String deviceName = mBTDevices.get(i).getName();
//        String deviceAddress = mBTDevices.get(i).getAddress();
//
//        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
//        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);
//
//
//        Log.d(TAG, "Trying to pair with " + deviceName);
//        mBTDevices.get(i).createBond();
//
//        mBTDevice = mBTDevices.get(i);
//        mBluetoothConnection = new BluetoothConnectionService(AutomaticActivity.this);
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle_automatic.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            return true;
        } else if (item.getItemId() == R.id.nav_manual) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, StartSingingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            mReadThread.stop();
            Intent intent = new Intent(AutomaticActivity.this, FAQActivity.class);
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
                    System.out.println("BATTERY LEVEL automatic: " + strInput);

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

