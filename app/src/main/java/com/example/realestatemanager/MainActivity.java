package com.example.realestatemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.example.realestatemanager.LoanSimulator.LoanSimulatorFragment;
import com.example.realestatemanager.Map.MapFragment;
import com.example.realestatemanager.RealtyAdd.RealtyAdd;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    SlidingPaneLayout pane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));

        DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.map_drawer) {
                replaceFragment(new MapFragment(), true);
            } else if (id == R.id.action_loan_simulator) {
                replaceFragment(new LoanSimulatorFragment(), false);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_activity_main_toolbar_add) {

            Intent intent = new Intent(this, RealtyAdd.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment, boolean isMaster) {
        int containerId = isMaster ? R.id.left_pane : R.id.right_pane;
        if (findViewById(containerId) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
        } else {
            Log.e("MainActivity", "Conteneur pour le fragment introuvable.");
        }
    }


}