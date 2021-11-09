package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AutomaticActivity extends AppCompatActivity {

    Button automatic_finish_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);

        Spinner cp_spinner = (Spinner) findViewById(R.id.cp_spinner);
        Spinner cp_spinner2 = (Spinner) findViewById(R.id.cp_spinner2);
        Spinner cp_spinner3 = (Spinner) findViewById(R.id.cp_spinner3);
        Spinner cp_spinner4 = (Spinner) findViewById(R.id.cp_spinner4);

        Spinner noh_spinner = (Spinner) findViewById(R.id.noh_spinner);


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

        ArrayAdapter<String> myHarmonyAdapter = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.number_of_harmonies));
        myHarmonyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noh_spinner.setAdapter(myHarmonyAdapter);


        automatic_finish_button = (Button)findViewById(R.id.automatic_finish_button);
        automatic_finish_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(AutomaticActivity.this, StartSingingActivity.class);
                startActivity(intent);
            }
        });
    }
}