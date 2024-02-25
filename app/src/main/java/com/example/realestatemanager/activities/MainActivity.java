package com.example.realestatemanager.activities;

import static com.google.android.gms.common.util.DeviceProperties.isTablet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.realestatemanager.R;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.fragments.DetailFragment;
import com.example.realestatemanager.fragments.ListFragment;
import com.example.realestatemanager.databinding.ActivityMainBinding;
import com.example.realestatemanager.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements OnListItemSelectedListener {
    private int currentPropertyId = -1;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private boolean isSearchFragmentDisplayed = false;

    public void onPropertySelected(int propertyId) {
        this.currentPropertyId = propertyId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initToolBar();
        initListeners();
        displayFragments();
        initFirstTabletItem();
        handleNavigationDrawer();

        if (getIntent().hasExtra("property_id")) {
            int propertyId = getIntent().getIntExtra("property_id", -1);
            if (propertyId != -1) {
                DetailFragment detailFragment = DetailFragment.newInstance(propertyId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_list_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    private void initViews() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initListeners() {
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRealtyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayFragments() {
        if (isTablet(getApplicationContext())) {
            // tablet
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentListContainer.getId(), new ListFragment())
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentDetailContainer.getId(), new DetailFragment())
                    .commit();
        } else {
            // smartphone
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentListContainer.getId(), new ListFragment())
                    .commit();
        }
    }

    private void initFirstTabletItem() {
        if (isTablet(getApplicationContext())) {
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentDetailContainer.getId(), DetailFragment.newInstance(1))
                    .commit();
        }
    }

    @Override
    public void onListFragmentDisplayed(boolean displayed) {
        if (displayed) {
            binding.addButton.setVisibility(View.VISIBLE);
        } else {
            binding.addButton.setVisibility(View.GONE);
        }
        invalidateOptionsMenu();
    }

    private void handleNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_first_activity) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_second_activity) {
                Intent intent = new Intent(MainActivity.this, SimulatorActivity.class);
                startActivity(intent);
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }


    public void setupDrawerToggle(boolean enableDrawer) {
        toggle.setDrawerIndicatorEnabled(enableDrawer);
        if (enableDrawer) {
            toggle.setToolbarNavigationClickListener(null);
            toggle.syncState();

            toggle.setDrawerSlideAnimationEnabled(true);
            binding.drawerLayout.addDrawerListener(toggle);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem editItem = menu.findItem(R.id.action_edit);
        editItem.setVisible(isTablet(getApplicationContext()) || binding.addButton.getVisibility() == View.GONE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home && !toggle.isDrawerIndicatorEnabled()) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_search) {
            displaySearchFragment();
            return true;
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (id == R.id.action_edit) {
            if (currentPropertyId != -1) {
                Intent intent = new Intent(this, EditPropertyActivity.class);
                intent.putExtra("PROPERTY_ID", currentPropertyId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No property selected", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displaySearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_list_container, searchFragment)
                .addToBackStack(null)
                .commit();

        // Mise à jour de l'indicateur pour refléter que le SearchFragment est affiché
        isSearchFragmentDisplayed = true;

        // Demande la mise à jour du menu pour refléter les changements de visibilité des icônes
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        // Si le drawer est ouvert, fermez-le
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Si le SearchFragment est affiché, mettez à jour l'indicateur et le menu
            if (isSearchFragmentDisplayed) {
                isSearchFragmentDisplayed = false;
                invalidateOptionsMenu(); // Mettre à jour le menu
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Récupérez les éléments de menu par leur ID
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem editItem = menu.findItem(R.id.action_edit);

        // Cachez les deux éléments si le SearchFragment est affiché
        boolean showItems = !isSearchFragmentDisplayed;
        if (searchItem != null && editItem != null) {
            searchItem.setVisible(showItems);
            editItem.setVisible(showItems);
        }

        return true;
    }

}