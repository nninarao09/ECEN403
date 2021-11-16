package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class ManualActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button manual_finish_button, generate_new_button;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout_manual;
    public ActionBarDrawerToggle actionBarDrawerToggle_manual;
    public int height = 80;
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
        DrawerLayout myLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout_manual);

        generate_new_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                for (int i=0; i<Integer.parseInt(number_of_harmonies.getText().toString()); i++){

//                    TextView tvNote = new TextView(ManualActivity.this);
//                    tvNote.setText("Notes");
//                    tvNote.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
//                    DrawerLayout.LayoutParams tvNoteLayout = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
//                    tvNoteLayout.setMargins(dpToPx(120), dpToPx(height_of_Titles), 0, 0);
//                    tvNote.setLayoutParams(tvNoteLayout);
//                    myLayout.addView(tvNote);
//
//                    TextView tvLength = new TextView(ManualActivity.this);
//                    tvLength.setText("Length");
//                    DrawerLayout.LayoutParams tvLengthLayout = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
//                    tvLengthLayout.setMargins(dpToPx(120)+dpToPx(left), dpToPx(height_of_Titles), 0, 0);
//                    tvLength.setLayoutParams(tvLengthLayout);
//                    myLayout.addView(tvLength);

                    for(int j=0; j<Integer.parseInt(notes_per_harmony.getText().toString()); j++){
//                        EditText editText = new EditText(ManualActivity.this);
//                        editText.setId(j+1);
//                        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
//                        lp.setMargins(0, dpToPx(height), 0, 0);
//                        editText.setLayoutParams(lp);
//                        editText.setGravity(1);
//                        editText.setHint("EditText "+(j+1));
//                        //editText.setHeight(dpToPx(height));
//                        myLayout.addView(editText);
//                        height = height + 30;
//                        left = left + 10;
//                        right = right + 10;
//                        bottom = bottom + 30;

                        String num = Integer.toString(i+1);
                        TextView HarmonyNumber = new TextView(ManualActivity.this);
                        HarmonyNumber.setText("Harmony " + num);
                        HarmonyNumber.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                        DrawerLayout.LayoutParams tvHarmonyNumber = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                        tvHarmonyNumber.setMargins(dpToPx(160), dpToPx(height_of_Harmony_Number), 0, 0);
                        HarmonyNumber.setLayoutParams(tvHarmonyNumber);
                        myLayout.addView(HarmonyNumber);


                        TextView tvNote = new TextView(ManualActivity.this);
                        tvNote.setText("Notes");
                        tvNote.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                        DrawerLayout.LayoutParams tvNoteLayout = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                        tvNoteLayout.setMargins(dpToPx(120), dpToPx(height_of_Titles), 0, 0);
                        tvNote.setLayoutParams(tvNoteLayout);
                        myLayout.addView(tvNote);

                        TextView tvLength = new TextView(ManualActivity.this);
                        tvLength.setText("Length");
                        DrawerLayout.LayoutParams tvLengthLayout = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                        tvLengthLayout.setMargins(dpToPx(110)+dpToPx(left), dpToPx(height_of_Titles), 0, 0);
                        tvLength.setLayoutParams(tvLengthLayout);
                        myLayout.addView(tvLength);

                        Spinner spinnerNote = new Spinner(ManualActivity.this);
                        ArrayAdapter<String> myNoteAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.notes));
                        myNoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(0, dpToPx(height), 0, 0);
                        spinnerNote.setLayoutParams(lp);
                        spinnerNote.setGravity(Gravity.RIGHT);
                        spinnerNote.setPadding(pxToDp(0), pxToDp(0), pxToDp(0), pxToDp(0));
                        //spinnerNote.setDropDownVerticalOffset(20);
                        spinnerNote.setId(j+1);
                        spinnerNote.setAdapter(myNoteAdapter);

                        myLayout.addView(spinnerNote);

                        height += 30;



//                        Spinner spinnerLength = new Spinner(ManualActivity.this);
//                        ArrayAdapter<String> myLengthAdapter = new ArrayAdapter<String>(ManualActivity.this,
//                                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.length));
//                        myLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                        DrawerLayout.LayoutParams lp2 = new DrawerLayout.LayoutParams(80,80);
//                        lp2.setMargins(0, dpToPx(height), 0, 0);
//                        spinnerLength.setLayoutParams(lp2);
//                        spinnerLength.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
//                        spinnerLength.setId(j+1);
//                        spinnerLength.setAdapter(myLengthAdapter);
//                        spinnerLength.setGravity(1);
//
//                        myLayout.addView(spinnerLength);

                        height += 30;

                    }
                    height_of_Titles += 50;
                    height_of_Harmony_Number += 60;

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