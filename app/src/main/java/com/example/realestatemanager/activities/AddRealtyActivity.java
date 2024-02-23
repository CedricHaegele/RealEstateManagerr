package com.example.realestatemanager.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.realestatemanager.R;
import com.example.realestatemanager.Utils;
import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.databinding.ActivityAddRealtyBinding;
import com.example.realestatemanager.model.AddressLoc;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewmodel.AddRealtyViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRealtyActivity extends AppCompatActivity {

    private final String TAG = AddRealtyActivity.class.getName();
    private ActivityAddRealtyBinding binding;
    private AddRealtyViewModel viewModel;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int REQUEST_PICK_IMAGE = 2;
    private final List<String> imageList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddRealtyViewModel.class);
        initView();
        initToolBar();
        setupListeners();
        checkAndRequestPermissions();
        initAgentList();
    }

    private void initView() {
        binding = ActivityAddRealtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.add_property));
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

    private void initAgentList() {
        AutoCompleteTextView agentAutoCompleteTextView = findViewById(R.id.edit_agent);
        String[] agents = getResources().getStringArray(R.array.real_estate_agents);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, agents);
        agentAutoCompleteTextView.setAdapter(adapter);
    }

    private void showPictureDialog() {
        CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.add_photo));

        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals(getString(R.string.take_photo))) {
                dispatchTakePictureIntent();
            } else if (items[item].equals(getString(R.string.choose_from_gallery))) {
                choosePhotoFromGallery();
            } else if (items[item].equals(getString(R.string.cancel))) {
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void choosePhotoFromGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap bitmap = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = (Bitmap) extras.get("data");
                }
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    Log.e(TAG, "Error processing gallery image", e);
                }
            }

            if (bitmap != null) {
                // Convert Bitmap to Base64
                String base64 = Utils.bitmapToBase64(bitmap);
                // Add base64 encoded image to list
                imageList.add(base64);
                // Update UI to reflect new image added
                updateRecyclerView();
            }
        }
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

    private void submitProperty() {
        RealEstate realEstate = populateRealEstate();
        if (realEstate != null) {
            Log.d(TAG, "realEstate: " + realEstate);
            viewModel.addProperty(realEstate).observe(this, id -> {
                Log.d(TAG, "realEstate is created: " + id);
                Utils.displayNotification(AddRealtyActivity.this, getString(R.string.successfully));
                finish();
            });
        }
    }

    private RealEstate populateRealEstate() {
        String title = binding.editTitle.getText().toString();
        String price = binding.editPrice.getText().toString().replaceAll("[$]", "");
        String surface = binding.editSurface.getText().toString();
        String address = binding.editAddress.getText().toString();
        String rooms = binding.roomsInput.getText().toString();
        String bedrooms = binding.bedroomsInput.getText().toString();
        String bathrooms = binding.bathroomsInput.getText().toString();
        String description = binding.descriptionInput.getText().toString();
        String agent = binding.editAgent.getText().toString();
        AddressLoc addressLoc = new AddressLoc();
        addressLoc.setAddressLabel(address);
        addressLoc.setLatLng(Utils.getLocationFromAddress(this, address));

        RealEstate realEstate = new RealEstate();
        realEstate.setTitle(title);
        realEstate.setPrice(price);
        realEstate.setSurface(surface);
        realEstate.setAddressLoc(addressLoc);
        realEstate.setRooms(rooms);
        realEstate.setBedrooms(bedrooms);
        realEstate.setBathrooms(bathrooms);
        realEstate.setDescription(description);
        realEstate.setAgent(agent);
        if (imageAdapter != null) {
            realEstate.setImageUrls(imageAdapter.getImages());
        }
        if (!validateInput(title, price, surface, address, rooms, bedrooms, bathrooms, description)) {
            Toast.makeText(this, getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
            return null;
        }
        return realEstate;
    }

    private boolean validateInput(String... inputs) {
        for (String input : inputs) {
            if (input.isEmpty()) {
                return false;
            }
        }
        return true;
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
