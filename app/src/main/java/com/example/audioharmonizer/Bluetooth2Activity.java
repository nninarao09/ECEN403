package com.example.audioharmonizer;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

public class Bluetooth2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    //WORKS

    Context context = this;

    private static final String TAG = "Bluetooth2Activity";
    //Make BT Adapter
    BluetoothAdapter mBluetoothAdapter;

    //Create ArrayList to Hold Devices Discovered
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    //Create DeviceListAdapter;
    public DeviceListAdapter mDeviceListAdapter;
    //Create Listview
    ListView lvNewDevices;
    //Declare those 2 New Buttons
    Button btnStartConnection;
    Button btnSend;
    //Create BluetoothConnectionService Object
    BluetoothConnectionService mBluetoothConnection;
    //Need UUID (Of Device?) (UUID 1 of 2)
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    //Create BluetoothDevice
    BluetoothDevice mBTDevice;
    //Create EditText
    EditText etSend;


    //Broadcast Receiver 1
    //Enable/Disable Bluetooth & ACTION_FOUND
    //Catches ALL State Changes to the BT
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device
            if(action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                //Define Integer to Define State From Intent
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);
                //Switch State with State Changes
                switch(state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive:STATE_OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1:STATE_TURNING_OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1:STATE_ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1:STATE_TURNING_ON");
                        break;
                }
            }
        }
    };

    //Broadcast Receiver 2
    //Discoverability
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //Getting Action & Detecting Different Scan Modes from BR1
            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                //Mode is Represented by an Integer
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
                switch (mode) {
                    //Device in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device Not in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled. Able to Receive Connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not Able to Receive Connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting...");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }
            }
        }
    };

    //Broadcast Receiver 3
    //Discover Devices & List Devices That Are Not Yet Paired
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");
            if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                //Log to Obtain Properties (Name, Address) of the Device
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress() + ".");
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                //Set Adapter to the List
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    //Broadcast Receiver 4
    //Pairing Devices
    private BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //Looking for Action Bond State Change
            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 Cases:
                //Case 1: Device Already Bonded
                if(mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG, "mBroadcastReceiver4: BOND_BONDED.");
                    //Assign Bluetooth Device to Bonded/ Paired Device
                    mBTDevice = mDevice;
                }
                //Case 2: Creating a Bond
                if(mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {Log.d(TAG, "mBroadcastReceiver4: BOND_BONDING.");}
                //Case 3: Bond is Broken
                if(mDevice.getBondState() == BluetoothDevice.BOND_NONE) {Log.d(TAG, "mBroadcastReceiver4: BOND_NONE.");}
            }
        }
    };

    //Insert an onDestroy() Method to Close BR's when App is Paused or Destroyed
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth2);

        //Declare Variables
        EditText etSend = (EditText) findViewById(R.id.editText);
        Button btnStartConnection = (Button) findViewById(R.id.btnStartConnection);
        Button btnSend = (Button) findViewById(R.id.btnSend);



        //Initialize ON/OFF Button
        Button btnONOFF = (Button) findViewById(R.id.btnONOFF);
        //Initialize discoverability Button
        Button btnDiscoverable_on_off = (Button) findViewById(R.id.btnDiscoverable_on_off);

        //Initialize ListView
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        //Initialize ArrayList
        mBTDevices = new ArrayList<>();

        //Create IntentFilter to Catch When Bond State Changes for BT Pairing Feature
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        //Register Another BroadcastReceiver (4) & Pass Filter
        registerReceiver(mBroadcastReceiver4, filter);

        //Get Default Adapter (Initialize BT Adapter Object)
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Set Item Click Listener
        lvNewDevices.setOnItemClickListener(Bluetooth2Activity.this);



        //Set On Click Listener for Button with 1 Method (enableDisableBT)
        btnONOFF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: enabling/disabling bluetooth called.");
                enableDisableBT();
            }
        });

        //OnClickListeners for Buttons
        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Bytes you will Send
                //Grab "EditText" from Text Field & Send it to the BluetoothConnectionService
                //Create ByteArray to Send Bytes to Connection Service Using the "write()" method
                byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });
    }

    //Create "startConnection()" Method
    //*Note: Connection will Fail & App will Crash if Pairing Has Not Occured First
    //Button Pressed When AcceptThread has Already Been Started & We are Ready to Start a Connection & Try to Initiate the ConnectedThread
    public void startConnection() {
        //Start Bluetooth Connection Method
        startBTConnection(mBTDevice, MY_UUID_INSECURE);
    }

    //Method to Initiate/ Start Chat Service
    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");
        //Call "startClient()" Method with BluetoothConnection Object & Pass Device & UUID
        mBluetoothConnection.startClient(device, uuid);
    }

    //BT Button Enable/Disable onClick Method
    public void enableDisableBT() {
        //3 Cases:
        //(1) BT Adapter null (device cannot support BT)
        if(mBluetoothAdapter == null) {Log.d(TAG, "enableDiableBT: DOes not have BT capacibilites.");}
        //(2) BT Adapter Not Enabled (Disabled)
        if(!mBluetoothAdapter.isEnabled()) {
            //Log to see the changes in the LOGCAT as Bluetooth does not work on Emulators
            Log.d(TAG, "enableDisableBT: enabling BT.");
            //Use Intent to Enable BT
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            //Filter that Intercepts Changes in the BT Status and Logs those changes
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            //mBroadcastReceiver1 will catch the BT Adapter "ACTION_STATE_CHANGED" State Changes & Log Those Changes
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        //(3) BT Adapter Enabled
        if(mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: disabling BT.");
            //Disable BT Adapter
            mBluetoothAdapter.disable();
            //Disable Discoverability***********
            //mBluetoothAdapter.cancelDiscovery();
            //Intent Filter to Catch State Change
            //Filter that Intercepts Changes in the BT Status and Logs those changes
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            //mBroadcastReceiver1 will catch the BT Adapter "ACTION_STATE_CHANGED" State Changes & Log Those Changes
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
    }

    //BT Button Discoverability onClick Method
    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");
        //Intent
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //Extra to Define Discoverable Duration (seconds) to Other Devices
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        //Intent Filter so BR2 Can Intercept the State Change
        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //BR2 Will be Looking for ACTION_SCAN_MODE
        registerReceiver(mBroadcastReceiver2, intentFilter);
    }

    //BT Button Discover onClick Method
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnDiscover(View view) {
        Log.d(TAG, "btnDiscover: Looking for Unpaired Devices.");

        //Case 1: If BT is Already in Discovery Mode (if the Device is Already Looking for Devices), End Discovering
        if(mBluetoothAdapter.isDiscovering()) {
            Log.d(TAG, "canceling discovery");
            //Cancel Discovery
            mBluetoothAdapter.cancelDiscovery();
            //*Note: If Device is Greater than LOLLIPOP, Require Special Permission Check to Start Discovery
            checkBTPermissions();
            //Start Discovery
            mBluetoothAdapter.startDiscovery();
            //Intent to Catch with BroadcastReceiver (uses ACTION_FOUND on BT Device)
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            //Register Receiver
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        //Case 2: If Not Discovering, Start Discovering
        if(!mBluetoothAdapter.isDiscovering()) {
            Log.d(TAG, "Discovering");
            //Check BT Permissions in Manifest
            checkBTPermissions();
            //Start Discovering
            mBluetoothAdapter.startDiscovery();
            //Intent to Catch with BroadcastReceiver (uses ACTION_FOUND on BT Device)
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            //Register Receiver
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    //Method to Check BT Permission for startDiscovery()
    //Method Required for All Devices Running API23+
    //Putting the Proper Permissions in the Manifest is Not Enough -> Android Must Programatically Check Permsisions for BT
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        //Method will Only Execute on Versions > LOLLIPOP; It is Not Needed Otherwise
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck != 0)  {this.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);}
            //1001 could be any number
            else{Log.d(TAG, "checkBTPermissions: No Need to Check Permissions. SDK Version < LOLLIPOP.");}
        }
    }

    //Method to Pair Bluetooth Devices
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Cancel Discovery (Memory Intensive)
        mBluetoothAdapter.cancelDiscovery();
        //Log Click
        Log.d(TAG, "onItemClick: Device Clicked.");
        //Get Device Name & Address Then Log Information
        String deviceName = mBTDevices.get(i).getName();
        String deviceAdresss = mBTDevices.get(i).getAddress();
        Log.d(TAG, "onItemClick: deviceName = " + deviceName + ".");
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAdresss + ".");
        Log.d(TAG, "INT I: " + i + ".");
        Log.d(TAG, "long l: " + l + ".");
        //Create Bond
        //*Note: Check API Version because "createBond()" Requires API19+
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();
            //Assign Bluetooth Device
            mBTDevice = mBTDevices.get(i);
            //Start Connection Service
            mBluetoothConnection = new BluetoothConnectionService(Bluetooth2Activity.this);
        }
    }

}