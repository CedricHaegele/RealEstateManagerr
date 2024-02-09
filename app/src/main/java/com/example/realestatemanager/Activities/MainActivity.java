package com.example.realestatemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realestatemanager.Fragments.DetailFragment;
import com.example.realestatemanager.Fragments.MainFragment;
import com.example.realestatemanager.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickedListener {

    private MainFragment mainFragment;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate: layout chargé");

        this.configureAndShowMainFragment();
        this.configureAndShowDetailFragment();
    }

    // --------------
    // CallBack
    // --------------
    @Override
    public void onButtonClicked(View view) {
        if (findViewById(R.id.frame_layout_detail) == null) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        } else {

        }
    }

    // --------------
    // FRAGMENTS
    // --------------
    private void configureAndShowMainFragment() {
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_main, mainFragment)
                    .commit();
        }
    }

    private void configureAndShowDetailFragment() {
        if (findViewById(R.id.frame_layout_detail) != null) {
            Log.d("MainActivity", "configureAndShowDetailFragmentIfNeeded: frame_layout_detail trouvé");
            detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

            if (detailFragment == null) {
                detailFragment = new DetailFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_detail, detailFragment)
                        .commit();
            }
        } else {
            Log.d("MainActivity", "Le conteneur frame_layout_detail n'existe pas dans le layout actuel.");
        }
    }

}