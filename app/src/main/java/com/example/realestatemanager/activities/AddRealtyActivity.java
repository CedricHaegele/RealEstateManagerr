package com.example.realestatemanager.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realestatemanager.R;
import com.example.realestatemanager.RealtyApp;
import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.databinding.ActivityAddRealtyBinding;
import com.example.realestatemanager.model.RealtyList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddRealtyActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int REQUEST_PICK_IMAGE = 2;
    private ActivityAddRealtyBinding binding;
    private List<String> imageList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private String imageEncoded;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolBar();
        setupListeners();
        checkAndRequestPermissions();
    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Add Realty");
        }
    }

    private void setupListeners() {
        binding.editPrice.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;

                    String str = s.toString();
                    // Vérifier si le texte contient déjà le symbole du dollar
                    if (!str.startsWith("$")) {
                        str = "$" + str;
                        binding.editPrice.setText(str);
                        binding.editPrice.setSelection(str.length()); // Positionner le curseur à la fin du texte
                    }

                    isUpdating = false;
                }
            }
        });

        // Les autres listeners restent inchangés
        binding.buttonPicture.setOnClickListener(v -> showPictureDialog());
        binding.addButton.setOnClickListener(v -> submitProperty());
    }


    private void showPictureDialog() {
        CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo");

        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                takePhotoFromCamera();
            } else if (items[item].equals("Choose from Gallery")) {
                choosePhotoFromGallery();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void checkAndRequestPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Gérer les résultats de la demande de permission
    }


    // Dans votre Activity ou Fragment où vous gérez la prise de photo
    private void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    // Remplacez 'BuildConfig.APPLICATION_ID' par votre applicationId sous forme de chaîne
                    String authority = "com.example.realestatemanager.provider";
                    photoURI = FileProvider.getUriForFile(this, authority, photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                Log.e("CameraError", "Erreur lors de la création du fichier photo", ex);
            }
        }
    }


    private void handleCameraImage() {
        if (photoURI != null) {
            Log.d("CameraImage", "URI de l'image: " + photoURI.toString());
            imageList.add(photoURI.toString());
            updateRecyclerView();
        } else {
            Log.e("CameraImage", "photoURI est null");
        }
    }


    private File createImageFile() throws IOException {
        // Création d'un nom de fichier unique
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    private void choosePhotoFromGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Gestion de la capture d'image
                handleCameraImage();
            } else if (requestCode == REQUEST_PICK_IMAGE) {
                // Gestion de la sélection d'image depuis la galerie
                handleGalleryImage(data);
            }
        }
    }
    private void handleGalleryImage(Intent data) {
        Uri selectedImageUri = data.getData();
        if (selectedImageUri != null) {
            String imagePath = selectedImageUri.toString();
            imageList.add(imagePath);
            updateRecyclerView();
        } else {
            Log.e("GalleryImage", "selectedImageUri est null");
        }
    }




    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void updateRecyclerView() {
        if (imageAdapter == null) {
            imageAdapter = new ImageAdapter(this, imageList);
            binding.photosRecyclerView.setAdapter(imageAdapter);
            binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            imageAdapter.notifyDataSetChanged();
        }
    }



    private void submitProperty() {
        String title = binding.editTitle.getText().toString();
        String priceStr = binding.editPrice.getText().toString().replaceAll("[$]", "");
        String price = binding.editPrice.getText().toString();
        String surface = binding.editSurface.getText().toString();
        String address = binding.editAddress.getText().toString();
        String rooms = binding.roomsInput.getText().toString();
        String bedrooms = binding.bedroomsInput.getText().toString();
        String bathrooms = binding.bathroomsInput.getText().toString();
        String description = binding.descriptionInput.getText().toString();
        String agent = binding.editAgent.getText().toString();

        int imageUrl = R.drawable.estate_image;

        if (validateInput(title, price, surface, address, rooms, bedrooms, bathrooms, description)) {
            addItemToDataBase(title, price, surface, rooms, bedrooms, bathrooms, description, address, String.valueOf(imageUrl), imageEncoded);
        } else {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void addItemToDataBase(String title, String price, String area, String rooms, String bedrooms, String bathrooms, String description, String address, String imagePath, String imageEncoded) {
        new Thread(() -> {
            // Utilisation de imageEncoded pour stocker l'image en Base64
            RealtyList realty = new RealtyList(title, price, address, imagePath, imageEncoded);
            // Autres configurations...
            RealtyApp.getInstance().getDatabase().realtyListDao().insert(realty);
            runOnUiThread(() -> Toast.makeText(this, "Property added successfully", Toast.LENGTH_SHORT).show());
        }).start();
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