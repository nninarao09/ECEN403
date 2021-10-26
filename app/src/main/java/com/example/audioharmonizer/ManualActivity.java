package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Spinner note_spinner = (Spinner) findViewById(R.id.note_spinner);
        Spinner length_spinner = (Spinner) findViewById(R.id.length_spinner);


        ArrayAdapter<String> myNoteAdapter = new ArrayAdapter<String>(ManualActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.notes));
        myNoteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        note_spinner.setAdapter(myNoteAdapter);

        ArrayAdapter<String> myLengthAdapter = new ArrayAdapter<String>(ManualActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.length));
        myLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        length_spinner.setAdapter(myLengthAdapter);

    }

}