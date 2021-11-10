package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AutomaticActivity extends AppCompatActivity {

    Button automatic_finish_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        Spinner cp_spinner = (Spinner) findViewById(R.id.cp_spinner);
        Spinner cp_spinner2 = (Spinner) findViewById(R.id.cp_spinner2);
        Spinner cp_spinner3 = (Spinner) findViewById(R.id.cp_spinner3);
        Spinner cp_spinner4 = (Spinner) findViewById(R.id.cp_spinner4);

        Spinner noh_spinner = (Spinner) findViewById(R.id.noh_spinner);

        //***************************Chord Progression Spinners***************************************

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
        //*********************************************************************************************************

        ArrayAdapter<String> myHarmonyAdapter = new ArrayAdapter<String>(AutomaticActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.number_of_harmonies));
        myHarmonyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noh_spinner.setAdapter(myHarmonyAdapter);


        automatic_finish_button = (Button)findViewById(R.id.automatic_finish_button);
        automatic_finish_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                //save spinner inputs
                String chord_spinner = cp_spinner.getSelectedItem().toString();
                String chord_spinner2 = cp_spinner2.getSelectedItem().toString();
                String chord_spinner3 = cp_spinner3.getSelectedItem().toString();
                String chord_spinner4 = cp_spinner4.getSelectedItem().toString();
                String harmony_spinner = noh_spinner.getSelectedItem().toString();



                globalVariable.getAutomaticArray()[4] = chord_spinner;
                globalVariable.getAutomaticArray()[5] = chord_spinner2;
                globalVariable.getAutomaticArray()[6] = chord_spinner3;
                globalVariable.getAutomaticArray()[7] = chord_spinner4;
                globalVariable.getAutomaticArray()[8] = harmony_spinner;


                for(int i=0; i<9; ++i){
                    showToast(globalVariable.getAutomaticArray()[i]);
                }

                Intent intent = new Intent(AutomaticActivity.this, StartSingingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

