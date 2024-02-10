package com.example.realestatemanager.activities;

import android.os.Bundle;
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

        // Configurer le bouton pour ajouter des photos
        binding.addPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour ajouter des photos
                Toast.makeText(AddRealtyActivity.this, "Add photos clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurer le FAB pour soumettre la propriété
        binding.submitPropertyFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour soumettre la propriété
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
        // Ici, vous pourriez par exemple créer un objet Propriété et l'envoyer à une base de données ou une API
    }
}
