package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutomaticActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button automatic_finish_button;//send button

    private static final String TAG = "AutomaticActivity";

    //BluetoothAdapter mBluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();;

    //BluetoothConnectionService mBluetoothConnection = new BluetoothConnectionService(AutomaticActivity.this);
    EditText etSend;

    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("F3694693-2D3F-43C1-BD05-1A9497862DC7");

    BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    public DrawerLayout drawerLayout_automatic;
    public ActionBarDrawerToggle actionBarDrawerToggle_automatic;
    private NavigationView navigationView;



    //*************************************bluetooth stuff below****************************************


    //*************************************bluetooth stuff above****************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        //BluetoothConnectionService mBluetoothConnection = new BluetoothConnectionService(AutomaticActivity.this);

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

        etSend = (EditText) findViewById(R.id.etSend);
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



                globalVariable.getAutomaticArray()[4] = chord_spinner;
                globalVariable.getAutomaticArray()[5] = chord_spinner2;
                globalVariable.getAutomaticArray()[6] = chord_spinner3;
                globalVariable.getAutomaticArray()[7] = chord_spinner4;
                globalVariable.getAutomaticArray()[8] = harmony_spinner;

                //for testing purposes
                //byte[] bytes = {1, 1, 1, 1, 1, 1, 1, 1, 1};
                for(int i=0; i<9; ++i){
                    showToast(globalVariable.getAutomaticArray()[i]);
                    //bytes = globalVariable.getAutomaticArray()[i].getBytes(Charset.defaultCharset());
                }


                //Send data to the device
//                mBluetoothConnection.startClient(globalVariable.getDevice(), MY_UUID_INSECURE);
//                byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());
//                mBluetoothConnection.write(bytes);


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
            Intent intent = new Intent(AutomaticActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(AutomaticActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(AutomaticActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            return true;
        } else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(AutomaticActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(AutomaticActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
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
}

