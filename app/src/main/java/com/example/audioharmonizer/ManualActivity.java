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
    public int height = 50;
    public int left = 10;
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

                    TextView tv = new TextView(ManualActivity.this);
                    tv.setText("Notes");
                    myLayout.addView(tv);

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



                        Spinner spinner = new Spinner(ManualActivity.this);
                        ArrayAdapter<String> myNoteAdapter = new ArrayAdapter<String>(ManualActivity.this,
                                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.notes));
                        myNoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        DrawerLayout.LayoutParams lp = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(dpToPx(left), dpToPx(height), dpToPx(right), dpToPx(bottom));
                        spinner.setLayoutParams(lp);
                        spinner.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
                        spinner.setId(j+1);
                        spinner.setPrompt("works");
                        spinner.setAdapter(myNoteAdapter);
                        spinner.setGravity(1);

                        myLayout.addView(spinner);


                        height = height + 30;
                        left = left + 10;
                        bottom = bottom + 10;
                        right = right + 30;

                    }
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