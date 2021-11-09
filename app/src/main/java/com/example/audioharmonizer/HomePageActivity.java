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

public class HomePageActivity extends AppCompatActivity {

    // define the global variable
    TextView question2;
    Button next_button;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
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
        //**********************************************************************


        // by ID we can use each component which id is assign in xml file
        // use findViewById() to get the both Button and textview
        next_button = (Button)findViewById(R.id.get_started);
        next_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                // Intents are objects of the android.content.Intent type. Your code can send them
                // to the Android system defining the components you are targeting.
                // Intent to start an activity called ThirdActivity with the following code:

                Intent intent = new Intent(HomePageActivity.this, InitialInputActivity.class);
//
//                // start the activity connect to the specified class
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

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch(item.getItemId()){
//
//            case R.id.nav_home: {
//                Navigation.findNavController(this, R.id.my_drawer_layout_home_page).navigate(R.id.HomePageActivityDestination);
//                break;
//            }
//            case R.id.nav_initial_inputs: {
//                Navigation.findNavController(this, R.id.my_drawer_layout_home_page).navigate(R.id.InitialInputActivityDestination);
//                break;
//            }
//        }
//        item.setChecked(true);
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch(item.getItemId()){
//            case R.id.nav_home: {
//                return true;
//            }
//
//            case R.id.nav_initial_inputs: {
//                return true;
//            }
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController)
//                || super.onOptionsItemSelected(item);
//    }

}

