package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ManualActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button manual_finish_button, generate_new_button;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout_manual;
    public ActionBarDrawerToggle actionBarDrawerToggle_manual;
    BluetoothAdapter mBlueAdapter;
    public int height = 120;
    public int height_of_Titles = 150;
    public int height_of_Harmony_Number = 120;
    public int manualArrayLength;
    public String delim = ";";
    public int numberHarmonyToSend;
    public int numberNotesToSend;

    private ReadInput mReadThread = null;
    int batteryLevel = 0;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.getManualArrayList().clear();

        initializeNavBar();

        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(ManualActivity.this, Bluetooth2Activity.class);
            startActivity(intent);
        }


        mReadThread = new ReadInput(globalVariable.getmBluetoothConnection().getSocket());


        EditText number_of_harmonies = (EditText) findViewById(R.id.number_of_harmonies);
        EditText notes_per_harmony = (EditText) findViewById(R.id.notes_per_harmony);

        generate_new_button = (Button)findViewById(R.id.generate_new);
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear_inside_scroll);
        //ScrollView myScrollview = findViewById(R.id.scrollView_manual);

        generate_new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    //creating the length array{
                    final ArrayList<String> lengthArray = new ArrayList<String>();
                    for(double i=0.5; i<=Integer.parseInt(globalVariable.getBeatsPerMeasure()); i+=0.5 ){
                        lengthArray.add(String.valueOf(i));
                    }

                    numberHarmonyToSend = Integer.parseInt(number_of_harmonies.getText().toString());
                    numberNotesToSend = Integer.parseInt(notes_per_harmony.getText().toString());


                    int NumberOfHarmonies = Integer.parseInt(number_of_harmonies.getText().toString());
                    int count = 1;
                    int k = 0;

                    manualArrayLength = numberHarmonyToSend * numberNotesToSend * 3;

                    while (NumberOfHarmonies != 0) {

                        String num = Integer.toString(count);
                        TextView HarmonyNumber = new TextView(ManualActivity.this);
                        HarmonyNumber.setText("Harmony " + num);
                        HarmonyNumber.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                        LinearLayout.LayoutParams tvHarmonyNumber = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        tvHarmonyNumber.setMargins(dpToPx(20), dpToPx(0), dpToPx(20), dpToPx(0));
                        HarmonyNumber.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                        HarmonyNumber.setLayoutParams(tvHarmonyNumber);
                        myLayout.addView(HarmonyNumber);


                        TextView tvNote = new TextView(ManualActivity.this);
                        tvNote.setText("Note, Octave, and Length Blocks:");
                        tvNote.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                        LinearLayout.LayoutParams tvNoteLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        tvNoteLayout.setMargins(dpToPx(20), dpToPx(10), dpToPx(20), dpToPx(0));
                        tvNote.setLayoutParams(tvNoteLayout);
                        //tvNote.setPadding(0, 0, 0, 0);
                        myLayout.addView(tvNote);

//                        TextView tvLength = new TextView(ManualActivity.this);
//                        tvLength.setText("Length");
//                        LinearLayout.LayoutParams tvLengthLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        tvLengthLayout.setMargins(dpToPx(0), dpToPx(0), 0, dpToPx(0));
//                        tvLength.setLayoutParams(tvLengthLayout);
//                        //tvLength.setPadding(0, 0, 0, 0);
//                        myLayout.addView(tvLength);

                        for (int j = 0; j < Integer.parseInt(notes_per_harmony.getText().toString()); j++) {
                            Spinner spinnerNote = new Spinner(ManualActivity.this);
                            ArrayAdapter<String> myNoteAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.notes));
                            myNoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(dpToPx(150), dpToPx(0), dpToPx(150), 0);
                            spinnerNote.setLayoutParams(lp);
                            spinnerNote.setGravity(Gravity.RIGHT);
                            spinnerNote.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                            spinnerNote.setId(k + j + 1);
                            spinnerNote.setAdapter(myNoteAdapter);
                            myLayout.addView(spinnerNote);

                            Spinner spinnerOctave = new Spinner(ManualActivity.this);
                            ArrayAdapter<String> myOctaveAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.octave));
                            myOctaveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp3.setMargins(dpToPx(150), dpToPx(0), dpToPx(150), 0);
                            spinnerOctave.setLayoutParams(lp3);
                            spinnerOctave.setGravity(Gravity.RIGHT);
                            spinnerOctave.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                            spinnerOctave.setId(k + j + 200);
                            spinnerOctave.setAdapter(myOctaveAdapter);
                            myLayout.addView(spinnerOctave);


                            Spinner spinnerLength = new Spinner(ManualActivity.this);
                            ArrayAdapter<String> myLengthAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                    android.R.layout.simple_list_item_1, lengthArray);
                            myLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(dpToPx(150), dpToPx(0), dpToPx(150), dpToPx(10));
                            spinnerLength.setLayoutParams(lp2);
                            spinnerLength.setGravity(Gravity.RIGHT);
                            spinnerNote.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                            spinnerLength.setId(k + j + 100);
                            spinnerLength.setAdapter(myLengthAdapter);
                            myLayout.addView(spinnerLength);

                        }

                        NumberOfHarmonies--;
                        count++;
                        k+=2;
                    }
                }

        });


        manual_finish_button = (Button)findViewById(R.id.finish_button);
        manual_finish_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                int NumberOfHarmonies = Integer.parseInt(number_of_harmonies.getText().toString());
                int count = 1;
                int k = 0;

                while (NumberOfHarmonies != 0) {
                    for (int j = 0; j < Integer.parseInt(notes_per_harmony.getText().toString()); j++) {
                        Spinner note_spinner = (Spinner) findViewById(k + j + 1);
                        String save = note_spinner.getSelectedItem().toString();
                        globalVariable.getManualArrayList().add(save);

                        Spinner octave_spinner = (Spinner) findViewById(k+j+200);
                        String saveOctave = octave_spinner.getSelectedItem().toString();
                        globalVariable.getManualArrayList().add(saveOctave);


                        Spinner length_spinner = (Spinner) findViewById(k+j+100);
                        String saveLength = length_spinner.getSelectedItem().toString();
                        globalVariable.getManualArrayList().add(saveLength);

                    }
                    NumberOfHarmonies--;
                    count++;
                    k+=2;
                }


                //Write out to esp32
                for(int i=0; i<4; ++i){
                    System.out.println("Manual Output: " + globalVariable.getInitialInputsArray()[i]);
                    globalVariable.getmBluetoothConnection().write(globalVariable.getInitialInputsArray()[i].getBytes(Charset.defaultCharset()));
                    globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));

                }

                //Here add length of manual array
                globalVariable.getmBluetoothConnection().write(Integer.toString(manualArrayLength).getBytes(Charset.defaultCharset()));
                globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));

                globalVariable.getmBluetoothConnection().write(Integer.toString(numberHarmonyToSend).getBytes(Charset.defaultCharset()));
                globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));

                globalVariable.getmBluetoothConnection().write(Integer.toString(numberNotesToSend).getBytes(Charset.defaultCharset()));
                globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));


                for(String i : globalVariable.getManualArrayList()){
                    System.out.println("Manual Output: " + i);
                    globalVariable.getmBluetoothConnection().write(i.getBytes(Charset.defaultCharset()));
                    globalVariable.getmBluetoothConnection().write(delim.getBytes(Charset.defaultCharset()));

                }

                mReadThread.stop();
                Intent intent = new Intent(ManualActivity.this, StartSingingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initializeNavBar(){
        drawerLayout_manual = findViewById(R.id.my_drawer_layout_manual);
        actionBarDrawerToggle_manual = new ActionBarDrawerToggle(this, drawerLayout_manual, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout_manual.addDrawerListener(actionBarDrawerToggle_manual);
        actionBarDrawerToggle_manual.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_manual);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(4).setChecked(true);
        onNavigationItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle_manual.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            return true;
        } else if (item.getItemId() == R.id.nav_start_singing) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, StartSingingActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            mReadThread.stop();
            Intent intent = new Intent(ManualActivity.this, FAQActivity.class);
            startActivity(intent);
        } else {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
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
                    System.out.println("BATTERY LEVEL manual: " + strInput);

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