package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BluetoothActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.audioharmonizer.MESSAGE";

    // define the global variables
    TextView question1;
    Button next_Activity_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the Button
        next_Activity_button = (Button)findViewById(R.id.first_activity_button);
        question1 = (TextView)findViewById(R.id.question1_id);

        // In question1 get the TextView use by findViewById()
        // In TextView set question Answer for message
        question1.setText("Q 1 - How to pass the data between activities in Android?\n"
                + "\n"
                + "Ans- Intent");

        // Add_button add clicklistener
        next_Activity_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called SecondActivity with the following code:

                Intent intent = new Intent(BluetoothActivity.this, HomePageActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }
}

//public class MainActivity extends AppCompatActivity {
//
//    public static final String EXTRA_MESSAGE = "com.example.audioharmonizer.MESSAGE";
//
//    private Button button;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        button = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(this, DisplayMessageActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    /** Called when the user taps the Send button */
//
//    public void showMessage() {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        startActivity(intent);
//    }
//
//}



