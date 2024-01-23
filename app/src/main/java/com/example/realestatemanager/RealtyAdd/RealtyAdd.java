package com.example.realestatemanager.RealtyAdd;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.realestatemanager.databinding.AddRealtyBinding;

public class RealtyAdd extends AppCompatActivity {
    private AddRealtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set a click listener for the 'Save' button
        binding.saveButton.setOnClickListener(v -> {
            // Logic to save the real estate data
            // Example: Fetching text from a TextInputEditText
            String propertyType = binding.propertyType.getText().toString();
            String price = binding.price.getText().toString();
            String area = binding.area.getText().toString();
            String rooms = binding.rooms.getText().toString();
            String address = binding.address.getText().toString();
            String interestPoints = binding.interestPoints.getText().toString();
            String status = binding.status.getText().toString();
            String marketEntryDate = binding.marketEntryDate.getText().toString();
            String saleDate = binding.saleDate.getText().toString();
            String realEstateAgent = binding.realEstateAgent.getText().toString();
            String description = binding.description.getText().toString();

            // Implement the saving logic here
        });

        // Set a click listener for the 'Add Photos' button
        binding.addPhotosButton.setOnClickListener(v -> {
            // Logic to add photos
            // Example: Opening a gallery or camera intent
        });

        // Additional logic for other UI components
        // Example: setting up dropdowns, additional buttons, etc.
    }
}
