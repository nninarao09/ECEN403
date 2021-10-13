package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    // define the global variable
    TextView question2;
    // Add button Move to next Activity and previous Activity
    Button next_button, previous_button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the both Button and textview

        next_button = (Button)findViewById(R.id.second_activity_next_button);
        previous_button = (Button)findViewById(R.id.second_activity_previous_button);
        question2 = (TextView)findViewById(R.id.question2_id);

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message

        question2.setText("Welcome to the Audio Harmonizer Homepage\n" +
                          "Please click next to continue to the next page"
                         );

        // Add_button add clicklistener
        next_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called ThirdActivity with the following code:

                Intent intent = new Intent(HomePageActivity.this, ModeOfOperationActivity.class);
//
//                // start the activity connect to the specified class
                startActivity(intent);
            }
        });

        // Add_button add clicklistener
        previous_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called oneActivity with the following code:

                Intent intent = new Intent(HomePageActivity.this, BluetoothActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }
}

//public class DisplayMessageActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_message);
//
//        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        // Capture the layout's TextView and set the string as its text
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(message);
//    }
//}