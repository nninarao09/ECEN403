package com.example.audioharmonizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // define the global variable
    TextView question2;
    Button next_button;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    //public AppBarConfiguration appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_initial_inputs));


    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //**********************NavBar Functionality**********************************
        drawerLayout = findViewById(R.id.my_drawer_layout_home_page);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigationview_id_home);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItem = navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(menuItem);
        //**********************************************************************


        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the both Button and textview
        next_button = (Button)findViewById(R.id.get_started);
        next_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent intent = new Intent(HomePageActivity.this, InitialInputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            return true;
        } else if (item.getItemId() == R.id.nav_initial_inputs) {
            Intent intent = new Intent(HomePageActivity.this, InitialInputActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_modes) {
            Intent intent = new Intent(HomePageActivity.this, ModeOfOperationActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.nav_automatic) {
            Intent intent = new Intent(HomePageActivity.this, AutomaticActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_manual) {
            Intent intent = new Intent(HomePageActivity.this, ManualActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_recordings) {
            Intent intent = new Intent(HomePageActivity.this, RecordingsActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.nav_faq) {
            Intent intent = new Intent(HomePageActivity.this, FAQActivity.class);
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

