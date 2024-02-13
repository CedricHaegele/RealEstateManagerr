package com.example.realestatemanager.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realestatemanager.R;
import com.example.realestatemanager.databinding.ActivityAddRealtyBinding;

public class AddRealtyActivity extends AppCompatActivity {

    private ActivityAddRealtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        initSpinner();
        setupListeners();
    }

    private void initSpinner() {
        // Initialisation du Spinner pour l'agent immobilier
        ArrayAdapter<CharSequence> agentAdapter = ArrayAdapter.createFromResource(this,
                R.array.real_estate_agents, android.R.layout.simple_spinner_item);
        agentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.agentInput.setAdapter(agentAdapter);

        // Initialisation du Spinner pour les catégories de propriétés
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.property_types, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categoryInput.setAdapter(categoryAdapter);
    }



    private void setupListeners() {
        binding.selectDescPictureButton.setOnClickListener(v -> {
            // Code pour sélectionner une photo de description
            Toast.makeText(AddRealtyActivity.this, "Select description photo clicked", Toast.LENGTH_SHORT).show();
        });

        binding.selectGalleryPictureButton.setOnClickListener(v -> {
            // Code pour sélectionner une photo de la galerie
            Toast.makeText(AddRealtyActivity.this, "Select gallery photo clicked", Toast.LENGTH_SHORT).show();
        });

        binding.addButton.setOnClickListener(v -> submitProperty());
    }

    private void submitProperty() {
        String category = binding.categoryInput.getSelectedItem().toString();
        String district = binding.districtInput.getText().toString();
        String price = binding.priceInput.getText().toString();
        String area = binding.areaInput.getText().toString();
        String rooms = binding.roomsInput.getText().toString();
        String bedrooms = binding.bedroomsInput.getText().toString();
        String bathrooms = binding.bathroomsInput.getText().toString();
        String description = binding.descriptionInput.getText().toString();

        Toast.makeText(this, "Property Category: " + category, Toast.LENGTH_SHORT).show();

    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Add Realty");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
