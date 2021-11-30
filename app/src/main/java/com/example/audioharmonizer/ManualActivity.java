package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ManualActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button manual_finish_button, generate_new_button;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout_manual;
    public ActionBarDrawerToggle actionBarDrawerToggle_manual;
    BluetoothAdapter mBlueAdapter;
    public int height = 120;
    public int height_of_Titles = 150;
    public int height_of_Harmony_Number = 120;
    public int left = 120;
    public int right = 10;
    public int bottom = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        initializeNavBar();

        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(ManualActivity.this, BluetoothActivity.class);
            startActivity(intent);
        }

//        Spinner note_spinner = (Spinner) findViewById(R.id.note_spinner);
//        Spinner length_spinner = (Spinner) findViewById(R.id.length_spinner);
//
//
//        ArrayAdapter<String> myNoteAdapter = new ArrayAdapter<String>(ManualActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.notes));
//        myNoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        note_spinner.setAdapter(myNoteAdapter);
//
//        ArrayAdapter<String> myLengthAdapter = new ArrayAdapter<String>(ManualActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.length));
//        myLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        length_spinner.setAdapter(myLengthAdapter);

        EditText number_of_harmonies = (EditText) findViewById(R.id.number_of_harmonies);
        EditText notes_per_harmony = (EditText) findViewById(R.id.notes_per_harmony);

        generate_new_button = (Button)findViewById(R.id.generate_new);
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.linear_inside_scroll);
        //ScrollView myScrollview = findViewById(R.id.scrollView_manual);

        generate_new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if (number_of_harmonies != null | notes_per_harmony != null) {

                    int NumberOfHarmonies = Integer.parseInt(number_of_harmonies.getText().toString());
                    int count = 1;

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
                        tvNote.setText("Note and Length Pairs:");
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
                            lp.setMargins(dpToPx(160), dpToPx(0), dpToPx(160), 0);
                            spinnerNote.setLayoutParams(lp);
                            spinnerNote.setGravity(Gravity.RIGHT);
                            spinnerNote.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                            spinnerNote.setId(j + 1);
                            spinnerNote.setAdapter(myNoteAdapter);
                            myLayout.addView(spinnerNote);


                            Spinner spinnerLength = new Spinner(ManualActivity.this);
                            ArrayAdapter<String> myLengthAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.length));
                            myLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(dpToPx(160), dpToPx(0), dpToPx(160), dpToPx(10));
                            spinnerLength.setLayoutParams(lp2);
                            spinnerLength.setGravity(Gravity.RIGHT);
                            spinnerNote.setPadding(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
                            spinnerLength.setId(j + 1);
                            spinnerLength.setAdapter(myLengthAdapter);
                            myLayout.addView(spinnerLength);

                            height += 60;
                        }
                        height += 30;
                        height_of_Titles += 50;
                        height_of_Harmony_Number += 60;
                        NumberOfHarmonies--;
                        count++;
                    }
                }

        });


        manual_finish_button = (Button)findViewById(R.id.finish_button);
        manual_finish_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

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
            Intent intent = new Intent(ManualActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(ManualActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(ManualActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(ManualActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            return true;
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(ManualActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
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

}