package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class StartSingingActivity extends AppCompatActivity {

    Button start_singing_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_singing);


        start_singing_button = (Button)findViewById(R.id.start_singing_button);
        start_singing_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //Start the countdown
                //beats per minute - how many counts to show
                //beats per measure - how fast to count down
            }
        });


    }

}