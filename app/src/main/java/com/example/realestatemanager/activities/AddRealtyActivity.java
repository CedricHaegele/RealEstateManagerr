package com.example.realestatemanager.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.realestatemanager.databinding.ActivityAddRealtyBinding;

public class AddRealtyActivity extends AppCompatActivity {

    private ActivityAddRealtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intToolBar();

        binding.addPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour ajouter des photos
                Toast.makeText(AddRealtyActivity.this, "Add photos clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.submitPropertyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProperty();
            }
        });
    }

    private void submitProperty() {
        // Récupération des valeurs des EditText avec ViewBinding
        String price = binding.propertyPriceEditText.getText().toString();
        String surface = binding.propertySurfaceEditText.getText().toString();
        String rooms = binding.propertyRoomsEditText.getText().toString();
        String description = binding.propertyDescriptionEditText.getText().toString();
        String address = binding.propertyAddressEditText.getText().toString();
        String availableDate = binding.propertyAvailableDateEditText.getText().toString();
        String soldDate = binding.propertySoldDateEditText.getText().toString();

        // Exemple d'utilisation des valeurs récupérées
        Toast.makeText(this, "Submitting property...", Toast.LENGTH_SHORT).show();

    }

    private void intToolBar() {

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
