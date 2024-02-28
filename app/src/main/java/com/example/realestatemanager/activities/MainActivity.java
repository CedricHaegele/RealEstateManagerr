package com.example.realestatemanager.activities;

import static com.google.android.gms.common.util.DeviceProperties.isTablet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.realestatemanager.R;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.fragments.DetailFragment;
import com.example.realestatemanager.fragments.ListFragment;
import com.example.realestatemanager.databinding.ActivityMainBinding;
import com.example.realestatemanager.fragments.SearchFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnListItemSelectedListener {
    public static final String EXTRA_PROPERTY_ID = "EXTRA_PROPERTY_ID";
    private int currentPropertyId = -1;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private boolean isSearchFragmentDisplayed = false;

    @Override
    public void onPropertySelected(int propertyId) {
        currentPropertyId = propertyId;
        DetailFragment detailFragment = DetailFragment.newInstance(propertyId);

        if (isTablet(getApplicationContext())) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, detailFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initListeners();
        displayFragments();
        initFirstTabletItem();
        handleNavigationDrawer();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            initToolBar();
        } else {
            Log.e("MainActivity", "ActionBar is null");
        }

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_PROPERTY_ID)) {
            int propertyId = intent.getIntExtra(EXTRA_PROPERTY_ID, -1);
            if (propertyId != -1) {
                DetailFragment detailFragment = DetailFragment.newInstance(propertyId);
                int containerId = isTablet(getApplicationContext()) ? R.id.fragment_detail_container : R.id.fragment_list_container;
                getSupportFragmentManager().beginTransaction()
                        .replace(containerId, detailFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle.syncState();
    }

    private void initViews() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initToolBar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initListeners() {
        binding.addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddRealtyActivity.class);
            startActivity(intent);
        });
    }

    private void displayFragments() {
        if (isTablet(getApplicationContext())) {
            // tablet
            getSupportFragmentManager().beginTransaction()
                    .replace(binding.fragmentListContainer.getId(), new ListFragment())
                    .commit();
            assert binding.fragmentDetailContainer != null;
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
            assert binding.fragmentDetailContainer != null;
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
            if (id == R.id.nav_list) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_list_container, new ListFragment())
                        .commit();
            } else if (id == R.id.nav_first_activity) {
                Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(mapIntent);
            } else if (id == R.id.nav_second_activity) {
                Intent simulatorIntent = new Intent(MainActivity.this, SimulatorActivity.class);
                startActivity(simulatorIntent);
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
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

        isSearchFragmentDisplayed = true;

        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem editItem = menu.findItem(R.id.action_edit);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (editItem != null) {
            editItem.setVisible(true);
        }
        if (searchItem != null) {
            searchItem.setVisible(true);
        }
        return true;
    }


}