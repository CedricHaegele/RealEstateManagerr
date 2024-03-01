package com.example.realestatemanager.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        setupStatusDropdown();
        setupDatePickers();
    }

    private void setupStatusDropdown() {
        String[] statuses = {"On Sale", "Sold"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, statuses);
        binding.statusAutoCompleteTextView.setAdapter(statusAdapter);
    }

    private void setupDatePickers() {
        binding.marketDateButton.setOnClickListener(view -> showDatePickerDialog(true));
        binding.soldDateButton.setOnClickListener(view -> showDatePickerDialog(false));
    }

    private void showDatePickerDialog(boolean isMarketDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    // Format de la date: AAAA-MM-JJ
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    if (isMarketDate) {
                        binding.marketDateButton.setText(selectedDate);
                    } else {
                        binding.soldDateButton.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
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
        pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    addImageToList(bitmap);
                }
            } else if (requestCode == REQUEST_PICK_IMAGE) {
                if (data.getClipData() != null) {

                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            addImageToList(bitmap);
                        } catch (IOException e) {
                            Log.e(TAG, "Error processing gallery image", e);
                        }
                    }
                } else if (data.getData() != null) {

                    Uri imageUri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        addImageToList(bitmap);
                    } catch (IOException e) {
                        Log.e(TAG, "Error processing gallery image", e);
                    }
                }
            }
            updateRecyclerView();
        }
    }

    private void addImageToList(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream); // Compresser l'image
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        imageList.add(encodedImage);
    }


    @SuppressLint("NotifyDataSetChanged")
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
        String title = Objects.requireNonNull(binding.editTitle.getText()).toString();
        String price = Objects.requireNonNull(binding.editPrice.getText()).toString().replaceAll("[$]", "");
        String surface = Objects.requireNonNull(binding.editSurface.getText()).toString();
        String address = Objects.requireNonNull(binding.editAddress.getText()).toString();
        String rooms = Objects.requireNonNull(binding.roomsInput.getText()).toString();
        String bedrooms = Objects.requireNonNull(binding.bedroomsInput.getText()).toString();
        String bathrooms = Objects.requireNonNull(binding.bathroomsInput.getText()).toString();
        String description = binding.descriptionInput.getText().toString();
        String agent = binding.editAgent.getText().toString();
        String status = binding.statusAutoCompleteTextView.getText().toString();
        String marketDateStr = binding.marketDateButton.getText().toString();
        String soldDateStr = binding.soldDateButton.getText().toString();
        Date marketDate = Utils.convertStringToDate(marketDateStr);
        Date soldDate = Utils.convertStringToDate(soldDateStr);


        if (validateInput(title, price, surface, address, rooms, bedrooms, bathrooms, description, agent, status, marketDateStr, soldDateStr) || imageList.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
            return;
        }

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
        realEstate.setStatus(status);
        realEstate.setMarketDate(marketDate);
        realEstate.setSoldDate(soldDate);
        if (imageAdapter != null) {
            realEstate.setImageUrls(imageAdapter.getImages());
        }

        viewModel.addProperty(realEstate).observe(this, id -> {
            Log.d(TAG, "RealEstate is created: " + id);
            Utils.displayNotification(AddRealtyActivity.this, getString(R.string.successfully_added_property));
            finish();
        });
    }

    private boolean validateInput(String title, String price, String surface, String address, String rooms, String bedrooms, String bathrooms, String description, String agent, String status, String marketDateStr, String soldDateStr) {
        if (title.isEmpty() || price.isEmpty() || surface.isEmpty() || address.isEmpty() || rooms.isEmpty() || bedrooms.isEmpty() || bathrooms.isEmpty() || description.isEmpty() || agent.isEmpty() || marketDateStr.isEmpty()) {
            return true;
        }

        return "Sold".equals(status) && soldDateStr.isEmpty();// Passed all validations
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
