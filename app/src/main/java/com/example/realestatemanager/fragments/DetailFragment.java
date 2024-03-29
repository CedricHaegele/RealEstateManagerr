package com.example.realestatemanager.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.example.realestatemanager.activities.MainActivity;
import com.example.realestatemanager.viewmodel.AddRealtyViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.realestatemanager.R;
import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.databinding.FragmentDetailBinding;
import com.example.realestatemanager.model.RealEstate;
import com.google.android.gms.maps.model.LatLng;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailFragment extends Fragment {
    private AddRealtyViewModel realtyEstateViewModel;
    private FragmentDetailBinding binding;
    private static final String ARG_ID = "id";
    private LatLng latLng;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(int id) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realtyEstateViewModel = new ViewModelProvider(requireActivity()).get(AddRealtyViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            int propertyId = getArguments().getInt(ARG_ID);
            realtyEstateViewModel.getRealEstate(propertyId).observe(getViewLifecycleOwner(), this::populateRealEstate);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    private void populateRealEstate(RealEstate realEstate) {
        if (realEstate != null) {
            binding.propertyTitle.setText("Title : " + realEstate.getTitle());
            binding.propertyDescription.setText("Description : " + realEstate.getDescription());
            binding.propertyPrice.setText("Price : $ " + realEstate.getPrice());
            binding.propertySurface.setText("Surface : " + realEstate.getSurface());
            binding.propertyAddress.setText("Address : " + realEstate.getAddressLoc().getAddressLabel());
            binding.propertyAgent.setText("Agent : " + realEstate.getAgent());
            binding.propertyRooms.setText("Rooms : " + realEstate.getRooms());
            binding.propertyBedrooms.setText("Bedrooms : " + realEstate.getBedrooms());
            binding.propertyBathrooms.setText("Bathrooms : " + realEstate.getBathrooms());
            binding.schoolCheckbox.setChecked(realEstate.hasSchoolNearby());
            binding.shoppingCheckbox.setChecked(realEstate.hasShoppingNearby());
            binding.transportCheckbox.setChecked(realEstate.hasTransportNearby());
            binding.poolCheckbox.setChecked(realEstate.hasPoolNearby());
            binding.propertyTitle.setText("Title : " + realEstate.getTitle());
            binding.propertyDescription.setText("Description : " + realEstate.getDescription());
            binding.statusAutoCompleteTextView.setText(realEstate.getStatus());

            if (realEstate.getMarketDate() != null) {
                String marketDateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(realEstate.getMarketDate());
                binding.marketDateEditText.setText(marketDateStr);
            } else {
                binding.marketDateEditText.setText("");
            }

            if ("Sold".equals(realEstate.getStatus()) && realEstate.getSoldDate() != null) {
                String soldDateStr = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(realEstate.getSoldDate());
                binding.soldDateEditText.setText(soldDateStr);
                binding.soldDateEditText.setVisibility(View.VISIBLE);
            } else {
                binding.soldDateEditText.setVisibility(View.GONE);
            }

            if (realEstate.getAddressLoc() != null && realEstate.getAddressLoc().getLatLng() != null) {
                this.latLng = realEstate.getAddressLoc().getLatLng(); // Mise à jour correcte

                String apiKey = getString(R.string.Api_key);
                String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" +
                        latLng.latitude + "," + latLng.longitude +
                        "&zoom=15&size=200x200&maptype=roadmap" +
                        "&markers=color:red%7Clabel:S%7C" +
                        latLng.latitude + "," + latLng.longitude +
                        "&key=" + apiKey;

                Glide.with(this)
                        .load(mapUrl)
                        .placeholder(R.drawable.map_placeholder)
                        .listener(new RequestListener<>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("DetailFragment", "Erreur de chargement de la carte", e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.i("DetailFragment", "Carte chargée avec succès");
                                return false;
                            }
                        })
                        .into(binding.propertyMap);
            }

            binding.propertyRooms.setText(realEstate.getRooms());
            List<String> imageUrls = new ArrayList<>(realEstate.getImageUrls());
            ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageUrls);
            binding.photosRecyclerView.setAdapter(imageAdapter);
            binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.propertyMap.setOnClickListener(v -> {
                if (latLng != null) {
                    String geoUri = "geo:" + latLng.latitude + "," + latLng.longitude + "?q=" + latLng.latitude + "," + latLng.longitude + "(Label)";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(intent);
                }

            });
        }
    }
}