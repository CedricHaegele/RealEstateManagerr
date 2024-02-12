package com.example.realestatemanager.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.databinding.FragmentDetailBinding;
import com.bumptech.glide.Glide;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_ADDRESS = "address";
    private static final String ARG_IMAGE_URL = "imageUrl";

    public static DetailFragment newInstance(String title, String description, String address, int imageUrl) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_ADDRESS, address);
        args.putInt(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            binding.titleTextView.setText(getArguments().getString(ARG_TITLE));
            binding.descriptionTextView.setText(getArguments().getString(ARG_DESCRIPTION));

            binding.addressTextView.setText(getArguments().getString(ARG_ADDRESS));

            int imageResId = getArguments().getInt(ARG_IMAGE_URL);
            Glide.with(this)
                    .load(imageResId)
                    .into(binding.imageView);

            // Activer le bouton de retour dans l'ActionBar
            if (getActivity() instanceof AppCompatActivity) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                ActionBar actionBar = activity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
            }

        }
    }
}

