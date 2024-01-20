package com.example.realestatemanager;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.realestatemanager.map.MapFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        DrawerLayout drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            // Gérer la navigation de l'élément de menu sélectionné
            int id = item.getItemId();
            if (id == R.id.map_drawer) {
                // Remplacer le fragment par MapFragment
                replaceFragment(new MapFragment());
            } else if (id == R.id.action_property_list) {
                // Remplacer le fragment par PropertyListFragment
                replaceFragment(new PropertyListFragment());
            } else if (id == R.id.action_loan_simulator) {
                // Remplacer le fragment par LoanSimulatorFragment
                // replaceFragment(new LoanSimulatorFragment());
            }

            // Fermer le tiroir après la sélection de l'élément
            DrawerLayout drawerLayout1 = findViewById(R.id.activity_main_drawer_layout);
            drawerLayout1.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu_toolbar, menu);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map_container, fragment)
                .commit();
    }
}