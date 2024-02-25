package com.example.realestatemanager.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.realestatemanager.R;
import com.example.realestatemanager.databinding.ActivityEditPropertyBinding;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewmodel.RealEstateViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditPropertyActivity extends AppCompatActivity {
    private ActivityEditPropertyBinding binding;
    private RealEstateViewModel realEstateViewModel;
    private RealEstate currentRealEstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        realEstateViewModel = new ViewModelProvider(this).get(RealEstateViewModel.class);

        int propertyId = getIntent().getIntExtra("PROPERTY_ID", -1);
        loadPropertyDetails(propertyId);
        handleFormSubmission();
    }

    private void loadPropertyDetails(int propertyId) {
        realEstateViewModel.getRealEstate(propertyId).observe(this, realEstate -> {
            if (realEstate != null) {
                currentRealEstate = realEstate;
                binding.editTextTitle.setText(realEstate.getTitle());
                binding.editTextPrice.setText(realEstate.getPrice());
                // Ajoutez des chargements pour les autres champs ici
            }
        });
    }

    private void handleFormSubmission() {
        binding.buttonUpdateProperty.setOnClickListener(v -> {
            if (validateInput()) {
                // Mise à jour de currentRealEstate avec les nouvelles valeurs
                updateRealEstateFromInput();
                realEstateViewModel.updateProperty(currentRealEstate);
                Toast.makeText(this, "Property updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please check your input", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput() {
        // Validation basique des entrées. Ajoutez plus selon vos besoins.
        return !TextUtils.isEmpty(binding.editTextTitle.getText().toString()) &&
                !TextUtils.isEmpty(binding.editTextPrice.getText().toString());
    }

    private void updateRealEstateFromInput() {
        currentRealEstate.setTitle(binding.editTextTitle.getText().toString());
        currentRealEstate.setPrice(binding.editTextPrice.getText().toString());
        // Mettez à jour les autres champs de currentRealEstate ici

        // Gestion des dates (exemple simple)
        String marketDateString = binding.editTextMarketDate.getText().toString();
        if (!TextUtils.isEmpty(marketDateString)) {
            try {
                Date marketDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(marketDateString);
                currentRealEstate.setMarketDate(marketDate);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
