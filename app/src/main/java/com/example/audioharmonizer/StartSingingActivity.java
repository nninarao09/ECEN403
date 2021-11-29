package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class StartSingingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    public int counter = 10;
    public int bpMinute = 0;
    public int bpMeasure = 0;
    Button start_singing_button;
    TextView countDown_textview;
    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_singing);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        bpMinute = Integer.parseInt(globalVariable.getBeatsPerMinute());
        bpMeasure = Integer.parseInt(globalVariable.getBeatsPerMeasure());


        //********************************bluetooth***********************************
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBlueAdapter.isEnabled()){
            showToast("You must turn bluetooth back on");
            Intent intent = new Intent(StartSingingActivity.this, BluetoothActivity.class);
            startActivity(intent);
        }


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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (actionBarDrawerToggle_automatic.onOptionsItemSelected(item)) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            Intent intent = new Intent(StartSingingActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(StartSingingActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(StartSingingActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(StartSingingActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(StartSingingActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(StartSingingActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            Intent intent = new Intent(StartSingingActivity.this, FAQActivity.class);
            startActivity(intent);
        } else {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}