package com.example.realestatemanager.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.R;

import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.databinding.FragmentDetailBinding;


import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_ADDRESS = "address";
    private static final String ARG_IMAGE_URL = "imageUrl";

    public DetailFragment() {

    }

    public static DetailFragment newInstance(String title, String address, String price, String imageUrl) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, address);
        args.putString(ARG_ADDRESS, price);
        args.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> imageUrls = new ArrayList<>();

        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageUrls);
        binding.photosRecyclerView.setAdapter(imageAdapter);
        binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Use the getArguments() method to retrieve the data passed to the fragment
        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE);
            String description = getArguments().getString(ARG_DESCRIPTION);
            String address = getArguments().getString(ARG_ADDRESS);
            String imageUrl = getArguments().getString(ARG_IMAGE_URL);

            // Set the text or image on the views using the binding object
            binding.propertyTitle.setText(title);
            binding.propertyDescription.setText(description);
            binding.propertyAddress.setText(address);
            binding.propertyRooms.setText(getString(R.string.number_of_rooms));
            binding.propertyType.setText(getString(R.string.property_type));
            binding.propertyPrice.setText(getString(R.string.price));

            // Example of setting a click listener using binding
            binding.buttonContactAgent.setOnClickListener(v -> {
                // Handle the click event
            });
        }
    }
}
