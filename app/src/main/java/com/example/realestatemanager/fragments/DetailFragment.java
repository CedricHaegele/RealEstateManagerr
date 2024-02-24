package com.example.realestatemanager.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.realestatemanager.viewmodel.RealtyListViewModel;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private RealtyListViewModel realtyListViewModel;
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
        realtyListViewModel = new ViewModelProvider(requireActivity()).get(RealtyListViewModel.class);
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
            realtyListViewModel.getRealEstate(getArguments().getInt(ARG_ID)).observe(getViewLifecycleOwner(), this::populateRealEstate);
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

            if (realEstate.getAddressLoc() != null && realEstate.getAddressLoc().getLatLng() != null) {
                this.latLng = realEstate.getAddressLoc().getLatLng();

                LatLng latLng = realEstate.getAddressLoc().getLatLng();
                if (latLng != null) {
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
                            .listener(new RequestListener<Drawable>() {
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
            }
            binding.propertyRooms.setText(realEstate.getRooms());
            List<String> imageUrls = new ArrayList<>(realEstate.getImageUrls());
            ImageAdapter imageAdapter = new ImageAdapter(getContext(), imageUrls);
            binding.photosRecyclerView.setAdapter(imageAdapter);
            binding.photosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            binding.propertyMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Vérifiez si LatLng est non null
                    if (latLng != null) {
                        String geoUri = "geo:0,0?q=" + latLng.latitude + "," + latLng.longitude + "(Label)";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Log.e("DetailFragment", "Aucune application pour ouvrir la carte n'a été trouvée.");
                        }
                    }
                }
            });

        }

    }

}
