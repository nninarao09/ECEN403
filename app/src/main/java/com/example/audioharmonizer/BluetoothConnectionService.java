package com.example.audioharmonizer;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothConnectionService {
    //Debugging TAG
    private static final String TAG = "BluetoothConnectionServ";
    //Chat Service Name
    private static final String appName = "AudioHarmonizer";
    //Create UUID (Unique Universal Identification Number) Variable (Of Device?) (UUID 2 of 2)
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    //Create Bluetooth Adapter to Handle Bluetooth Objects & Commands & Context
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    //Variable for InsecureAcceptThread
    private AcceptThread mInsecureAcceptThread;
    //Create ConnectThread Variable
    private ConnectThread mConnectThread;
    //Create Bluetooth Device Object
    private BluetoothDevice mmDevice;
    //Create Global UUID
    private UUID deviceUUID;
    //Create Progress Dialog
    ProgressDialog mProgressDialog;
    //Create ConnectedThread Object
    private ConnectedThread mConnectedThread;

    //Constructor
    public BluetoothConnectionService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //When BluetoothConnection Object is Created, Call "start()" Method [public synchronized]
        //Initiates AcceptThread
        start();
    }

    //Thread that will Always be Waiting for a Connection & Runs until a Connection is Accepted or Cancelled
    //Will run on another thread so it does not use up the main resources on MainActivity thread
    private class AcceptThread extends Thread { //AcceptThread Class
        //Local Bluetooth Server Socket
        private final BluetoothServerSocket mmServerSocket;

        //AcceptThread Constructor to Declare Server Socket
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            //Create New Listening Server Socket for Other Devices to Connect to with "appName" & "UUID"
            try {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
                //Log
                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            } catch (IOException e) {Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());}
            mmServerSocket = tmp;
        }

        //Run Method (will automatically execute inside the thread so do not worry about calling the method)
        public void run() {
            Log.d(TAG, "run: AcceptThread Running.:");
            BluetoothSocket socket = null;
            try {
                //A Blocking Call & Will Only Return on a Successful Connection or Exception
                Log.d(TAG, "run: RFCOM server socket start...");
                //Code with Sit & Wait Until a Connection is Made or Fails
                socket = mmServerSocket.accept();
                //If Successful Connection, Code Advances to this Line
                Log.d(TAG, "run: RFCOM server socket accepted connection.");
            } catch (IOException e) {Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());}
            //Test if Socket is Null (if yes, move on to the next step)
            if (socket != null) {connected(socket, mmDevice);}
            Log.i(TAG, "END mAcceptThread.");
        }

        //End/ Cancel AcceptThread Method
        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {mmServerSocket.close();
            }
            catch (IOException e) {Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed." + e.getMessage());}
        }
    }

    //Create ConnectThread Class (Initiates Bluetooth Connection with AcceptThread)
    //Thread will Run wile Attempting to Make an Outgoing Connection with a Device
    //*Note: Runs straight through -> connection succeeds or fails
    private class ConnectThread extends Thread {
        //Create Bluetooth Socket
        private BluetoothSocket mmSocket;

        //Create "ConnectThread" Default Constructor
        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device; //Bluetooth Device
            deviceUUID = uuid; //UUID
        }

        //Create Run Method (like what is in the AcceptThread)
        //Automatically Executes When a ConnectThread Object is Created
        public void run() {
            //Create Bluetooth Socket
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread.");
            //Get BluetoothSocket for Connection with BluetoothDevice
            try {
                Log.d(TAG, "ConnectThread: Trying to Create InsecureRfcommSocket using UUID: " + MY_UUID_INSECURE);
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: Could not Create InsecureRfcommSocket " + e.getMessage());
            }
            //Assign Socket to Temporary Variable
            mmSocket = tmp;
            //Cancel Discovery if Connection is Made (Memory Intensive)
            mBluetoothAdapter.cancelDiscovery();
            //A Blocking Call & Will Only Return on a Successful Connection or Exception
            try {
                mmSocket.connect();
                Log.d(TAG, "run: ConnectThread connected."); //Connection Successfully Established
            } catch (IOException e) {
                //Close Socket
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to Close Connection in Socket " + e1.getMessage());
                }
                //If Connection Fails & Exception Thrown, Display Message
                Log.d(TAG, "run: ConnectThread: Could Not Connect to UUID: " + MY_UUID_INSECURE);
            }
            //If Exception was Not Thrown, Advance to This Part
            //Call Method "connected()" & Pass Socket "mmSocket" & Device "mmDevice"
            connected(mmSocket, mmDevice);
        }
        //Cancel Method to Cancel Connection
        public void cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            }
            catch (IOException e) {Log.e(TAG, "cancel: close() of mmSocket in ConnectThread failed." + e.getMessage());}
        }
    }

    //Method to Start Connection Service
    //Specifically Starts/Initiates AcceptThread to Begin "Listening (Server)" Mode
    //Called by Activity onResume()
    public synchronized void start() {
        //Method Specifically Starts AcceptThread
        Log.d(TAG, "start:");
        //If AcceptThread Exists, Cancel It & Start a New One (Cancel Any Thread Attempting to Make a Connection)
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        //If AcceptThread Does Not Exist, Start One
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start(); //*Note, this "start()" is not the same as the "start()" of this method
            //The "start()" Method Here is Native to the Thread Class & Initiates the Thread that Calls It
        }
    }

    //Method "StartClient" to Initiate the ConnectThread
    //While AcceptThread is Waiting for a Connection, ConnectThread Starts & Attempts to Make a Connection with Other Devices' "AcceptThread()"
    public void startClient(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startClient: Started.");
        //Create Progress Dialog Box to Appear when a Connection is Trying to be Made
        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth", "Please Wait...", true);
        //Declare ConnectThread Object & Start ConnectThread
        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start(); //*Note, this "start()" is not the same as the "start()" of this method
    }

    //"ConnectedThread" Class to Manage the Connection
    private class ConnectedThread extends Thread {
        //Create Variables (Socket, Input Stream, Output Stream)
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //Create Default Constructor
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");
            //Declare Variables (Socket, Input Stream, Output Stream)
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            //Dismiss ProgressDialog Box when Connection is Established
            try {
                mProgressDialog.dismiss();
            } catch (NullPointerException e) {e.printStackTrace();}


            //Get Inputs & Outputs from Streams
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Declare Input & Output Streams
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        //Create "run()" Method
        public void run() {
            //Create Byte-Array Object to Get Input from the Input Stream (aka Buffer Store for Stream)
            byte[] buffer = new byte[1024];
            //Create Integer to Read Input from Input Stream (bytes returned from "read()")
            int bytes;
            //While Loop: Keep Listening to the InputStream Until an Exception Occurs
            while (true) {
                //Read from the InputStream
                try {
                    bytes = mmInStream.read(buffer);
                    //Create String to Convert Incoming Message from "buffer" & "bytes" to "String"
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.d(TAG, "InputStream: " + incomingMessage);
                } catch (IOException e) {
                    Log.e(TAG, "write: Error reading InputStream." + e.getMessage());
                    break; //If there is a Problem with the InputStream, you want to Break the Loop & End the Connection
                }
            }
        }

        //Write Method to Send Data to Remote Device (called from MainAcitivity)
        public void write(byte[] bytes) {
            //Create String from the Bytes we are to Send to the "write()" Method
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to OutputStream: " + text);
            //Write to Output Stream
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to OutputStream." + e.getMessage());
            }
        }

        //Cancel Method to Shut Down the Connection (called from MainActivity)
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    //Create Method "connected()"
    //Manage Connection & Perform OutputStream Transmissions & Grab InputStream Transmissions
    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "connected: Starting.");
        //Thread to Manage Connection & Perform Transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        //Start ConnectedThread
        mConnectedThread.start();
    }

    //Another Write Method (previous method won't be accessible from MainActivity)
    //This Write Method can Access the Connection Service which can Access the ConnectedThread
    public void write(byte[] out) {
        //Create Temporary Object Thread
        ConnectedThread r;
        //Perform Write
        Log.d(TAG, "write: Write Called.");
        mConnectedThread.write(out);
    }
}
