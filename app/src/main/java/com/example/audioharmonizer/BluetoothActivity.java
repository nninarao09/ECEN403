package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.audioharmonizer.MESSAGE";
    private static final int REQUEST_ENABLE_BT =0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("F3694693-2D3F-43C1-BD05-1A9497862DC7");
    private static final UUID MY_UUID_INSECURE2 =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mPairedBtn, mDiscoverableBtn, mConnectESP;
    Boolean isConnected = false;

    Button bluetoothTest;

    BluetoothAdapter mBlueAdapter;
    private BluetoothSocket mBTSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        //**************bluetooth content below************************************************

        ///*
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);
        mDiscoverableBtn = findViewById(R.id.discoverableBtn);
        mConnectESP = findViewById(R.id.connect_ESP_button);
        bluetoothTest = findViewById(R.id.bluetooth_test);

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


        mDiscoverableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //do discoverable things
                //showToast("hello");
                if(!mBlueAdapter.isDiscovering()){
                    showToast("Making Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });
        //*/


        //*********************************************************************************
        //Checking if phone is connected to MCU
        mConnectESP.setOnClickListener(new View.OnClickListener() {
            // if connected to esp device move to next activity

            public void onClick(View v)
            {
                //if phone is connected to esp
                Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                for(BluetoothDevice device: devices){
                    if(device.getName().equals("Blank2")){ //Change this name to DSP name
                        globalVariable.setDevice(device);
                        //MY_UUID_INSECURE = UUID.fromString(device.GetUuids()[0].Uuid.ToString());
                        //
                        globalVariable.setmBluetoothConnection(new BluetoothConnectionService(BluetoothActivity.this));
                        globalVariable.getmBluetoothConnection().startClient(device, MY_UUID_INSECURE);
//                        String etSend = "TESTING Nina";
//                        byte[] bytes = etSend.getBytes(Charset.defaultCharset());
//                        globalVariable.getmBluetoothConnection().write(bytes);
                        //
                        Intent intent = new Intent(BluetoothActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        isConnected = true;
                    }
                }

                if(!isConnected){
                    showToast("Not Connected to the Audio Harmonizer, Please go to settings to connect");
                }

            }

        });


        bluetoothTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BluetoothActivity.this, Bluetooth2Activity.class);
                startActivity(intent);
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




