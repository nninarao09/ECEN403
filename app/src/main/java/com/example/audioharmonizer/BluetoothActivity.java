package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.audioharmonizer.MESSAGE";
    private static final int REQUEST_ENABLE_BT =0;


    // define the global variables

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mPairedBtn, mConnectESP;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //**************bluetooth content below************************************************

        ///*
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);
        mConnectESP = findViewById(R.id.connect_ESP_button);

        //adapter
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        //check if bluetooth is available or not
        if(mBlueAdapter == null){
            mStatusBlueTv.setText("Bluetooth is not available");
        } else{
            mStatusBlueTv.setText("Bluetooth is available");
        }

        // set image according to blutooth status(on/off)
        if(mBlueAdapter.isEnabled()){
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        } else{
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }

        //on btn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!mBlueAdapter.isEnabled()){
                    showToast("Turning on Bluetooth...");
                    //intenr to turn on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else{
                    showToast("Bluetooth is already on");
                }
            }
        });

        //off btn click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    showToast("Turning off Bluetooth...");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                } else{
                    showToast("Bluetooth is already off");
                }
            }
        });

        //get paired device click
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mBlueAdapter.isEnabled()){
                    mPairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for(BluetoothDevice device: devices){
                        mPairedTv.append("\nDevice: " + device.getName()+ "," + device);
                    }

                } else{
                    //bluetooth is off so cant get paired devices
                    showToast("Turn on bluetooth to get paired devices");
                }

            }
        });
        //*/


        //*********************************************************************************
        //Checking connection to mcu
        mConnectESP.setOnClickListener(new View.OnClickListener() {
            // if connected to esp device move to next activity

            public void onClick(View v)
            {
                //if phone is connected to esp
                if(true){
                    Intent intent = new Intent(BluetoothActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else{
                    showToast("Not Connected to the Audio Harmonizer, Please go to settings to connect");
                }

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    //bluetooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                } else {
                    // user denied to turn bluetooth on
                    showToast("couldn't turn on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //end of it
}




