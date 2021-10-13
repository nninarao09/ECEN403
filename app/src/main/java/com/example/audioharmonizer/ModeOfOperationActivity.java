package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeOfOperationActivity extends AppCompatActivity {

    Button automatic_button, manual_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_of_operation);

        automatic_button = (Button)findViewById(R.id.automatic_button);
        manual_button = (Button)findViewById(R.id.manual_button);



        automatic_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ModeOfOperationActivity.this, AutomaticActivity.class);
                startActivity(intent);
            }
        });

        manual_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ModeOfOperationActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

    }
}