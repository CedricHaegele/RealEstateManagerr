package com.example.realestatemanager.RealtyAdd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.realestatemanager.databinding.AddRealtyBinding;
import com.example.realestatemanager.R;
import com.example.realestatemanager.entities.Realty;
import com.example.realestatemanager.viewmodel.RealtyViewModel;

public class RealtyAdd extends AppCompatActivity {

    private AddRealtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupListeners();
    }

    private void setupListeners() {
        binding.buttonAddPhotos.setOnClickListener(v -> openPhotoPicker());
        binding.buttonAddRealty.setOnClickListener(v -> saveRealty());
    }

    private void openPhotoPicker() {

        CharSequence[] options = {"Prendre une photo", "Choisir depuis la galerie", "Annuler"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une photo");

        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {

            } else if (which == 1) {

            }
        });

        builder.show();
    }


    private void saveRealty() {
        try {
            String city = binding.city.getText().toString();
            double price = Double.parseDouble(binding.editTextPrice.getText().toString());
            String description = binding.description.getText().toString();
            double surface = Double.parseDouble(binding.area.getText().toString());
            int bedrooms = Integer.parseInt(binding.bedrooms.getText().toString());

            int bathrooms = Integer.parseInt(binding.bathrooms.getText().toString());
            String address = binding.address.getText().toString();
            String agent = binding.realEstateAgent.getText().toString();

            if (city.isEmpty() || description.isEmpty() || address.isEmpty() || agent.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs nécessaires", Toast.LENGTH_SHORT).show();
                return;
            }

            Realty realty = new Realty(city, price, description, surface, bedrooms, bathrooms, address, agent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Erreur dans les données numériques saisies", Toast.LENGTH_SHORT).show();
        }
    }




}
