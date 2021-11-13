package com.example.audioharmonizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartSingingActivity extends AppCompatActivity {
    public int counter = 10;
    Button start_singing_button;
    TextView countDown_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_singing);


        start_singing_button = (Button)findViewById(R.id.start_singing_button);
        countDown_textview= (TextView) findViewById(R.id.countDown_textview);

        start_singing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 10;
                new CountDownTimer(10000, 1000){
                    public void onTick(long millisUntilFinished){
                        countDown_textview.setText(String.valueOf(counter));
                        counter--;
                    }
                    public  void onFinish(){
                        countDown_textview.setText("GO!!");
                    }
                }.start();
            }
        });


    }

}