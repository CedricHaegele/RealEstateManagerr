package com.example.realestatemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.adapter.ImageAdapter;
import com.example.realestatemanager.databinding.FragmentDetailBinding;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewmodel.RealEstateViewModel;


import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private static final String ARG_ID = "propertyId";
    private FragmentDetailBinding binding;
    private RealEstateViewModel realEstateViewModel;

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
        realEstateViewModel = new ViewModelProvider(requireActivity()).get(RealEstateViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_ID)) {
            int propertyId = getArguments().getInt(ARG_ID);
            realEstateViewModel.getRealEstate(propertyId).observe(getViewLifecycleOwner(), this::populateRealEstate);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateRealEstate(RealEstate realEstate) {
        if (realEstate != null) {
            binding.propertyTitle.setText(realEstate.getTitle());
            binding.propertyDescription.setText(realEstate.getDescription());
            if (realEstate.getAddressLoc() != null) {
                binding.propertyAddress.setText(realEstate.getAddressLoc().getAddressLabel());
            }
            binding.propertyRooms.setText(realEstate.getRooms());
            List<String> imageUrls = new ArrayList<>(realEstate.getImageUrls());
            ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageUrls);
            binding.photosRecyclerView.setAdapter(imageAdapter);
            binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
