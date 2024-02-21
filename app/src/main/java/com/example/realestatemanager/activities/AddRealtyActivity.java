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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realestatemanager.R;
import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.dao.PhotoDao;
import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.databinding.ActivityAddRealtyBinding;
import com.example.realestatemanager.model.Photo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddRealtyActivity extends AppCompatActivity {
    private ActivityAddRealtyBinding binding;
    private Uri photoURI;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int REQUEST_PICK_IMAGE = 2;
    private List<String> imageList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int currentRealtyId;
    private PhotoDao photoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        photoDao = AppDatabase.getInstance(getApplicationContext()).photoDao();

        initToolBar();
        setupListeners();
        checkAndRequestPermissions();
    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Add Property");
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

                    if (!str.startsWith("$")) {
                        str = "$" + str;
                        binding.editPrice.setText(str);
                        binding.editPrice.setSelection(str.length());
                    }

                    isUpdating = false;
                }
            }
        });

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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), PERMISSIONS_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void takePhotoFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                if (photoFile != null) {

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

    private File createImageFile() throws IOException {
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
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imageUri = photoURI;
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null) {
                imageUri = data.getData();
            } else {
                return;
            }

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                insertPhotoAndUpdateUI(bitmap);
            } catch (IOException e) {
                Log.e("ImageProcessingError", "Error processing image", e);
                Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertPhotoAndUpdateUI(Bitmap bitmap) {
        new Thread(() -> {
            Photo photo = new Photo();
            photo.setRealtyId(currentRealtyId);
            photo.setImage(bitmapToByteArray(bitmap));

            long photoId = photoDao.insert(photo);
            runOnUiThread(() -> {
                if (photoId != -1) {
                    Log.d("InsertPhoto", "Photo inserted successfully. ID: " + photoId);
                    // Mise à jour de l'UI ici si nécessaire
                    imageList.add("Un identifiant ou chemin pour l'image insérée"); // Mettre à jour avec des données réelles
                    updateRecyclerView();
                } else {
                    Toast.makeText(AddRealtyActivity.this, "Failed to add photo", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    private void updateRecyclerView() {
        if (imageAdapter == null) {
            imageAdapter = new ImageAdapter(this, imageList);
            binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.photosRecyclerView.setAdapter(imageAdapter);
        } else {
            imageAdapter.notifyDataSetChanged();
        }
    }


    private void handleCameraImage() {
        Bitmap bitmap = null;
        try {
            // Convertissez l'URI de l'image en Bitmap
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
            // Convertissez le Bitmap en tableau de bytes
            byte[] imageData = bitmapToByteArray(bitmap);

            // Créez un nouvel objet Photo
            Photo photo = new Photo();
            photo.setImage(imageData);
            // Assurez-vous de définir les autres propriétés de Photo si nécessaire
            // photo.setRealtyId(currentRealtyId);

            // Insérez la photo dans la base de données
            new Thread(() -> {
                long photoId = AppDatabase.getInstance(getApplicationContext()).photoDao().insert(photo);
                if (photoId != -1) {
                    Log.d("InsertPhoto", "Photo insérée avec succès. ID: " + photoId);
                    // Mettre à jour l'interface utilisateur si nécessaire...
                } else {
                    Log.e("InsertPhoto", "Échec de l'insertion de la photo.");
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void submitProperty() {
        String title = binding.editTitle.getText().toString();
        String priceStr = binding.editPrice.getText().toString().replaceAll("[$]", "");
        String surface = binding.editSurface.getText().toString();
        String address = binding.editAddress.getText().toString();
        String rooms = binding.roomsInput.getText().toString();
        String bedrooms = binding.bedroomsInput.getText().toString();
        String bathrooms = binding.bathroomsInput.getText().toString();
        String description = binding.descriptionInput.getText().toString();
        String agent = binding.editAgent.getText().toString();

        if (validateInput(title, priceStr, surface, address, rooms, bedrooms, bathrooms, description, agent)) {
            // Ici, vous pouvez traiter les informations de propriété
            // Pour les images, vous les traitez déjà dans onActivityResult
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

    private void addItemToDataBase(String title, String price, String area, String rooms, String bedrooms, String bathrooms, String description, String address, Bitmap bitmap) {
        // Assurez-vous que bitmap n'est pas null avant de l'utiliser
        if (bitmap != null) {
            new Thread(() -> {
                Photo photo = new Photo();
                byte[] imageData = bitmapToByteArray(bitmap);
                photo.setImage(imageData);
                photo.setRealtyId(currentRealtyId); // Assurez-vous de définir le currentRealtyId correctement

                Log.d("AddRealtyActivity", "Inserting photo with Realty ID: " + currentRealtyId);
                long photoId = photoDao.insert(photo);
                Log.d("AddRealtyActivity", "Photo inserted with ID: " + photoId);

                runOnUiThread(() -> {
                    if (photoId != -1) {
                        Log.d("InsertPhoto", "Photo insérée avec succès. ID: " + photoId);
                        // Mettre à jour l'interface utilisateur si nécessaire
                    } else {
                        Log.e("InsertPhoto", "Échec de l'insertion de la photo.");
                    }
                });
            }).start();
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