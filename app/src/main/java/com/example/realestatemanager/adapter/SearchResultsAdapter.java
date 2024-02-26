package com.example.realestatemanager.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestatemanager.Utils;
import com.example.realestatemanager.databinding.ItemRealestateBinding;
import com.example.realestatemanager.model.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {
    private List<RealEstate> realEstates = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRealestateBinding binding = ItemRealestateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealEstate realEstate = realEstates.get(position);
        holder.binding.titleTextView.setText(realEstate.getTitle());
        holder.binding.descriptionTextView.setText(realEstate.getDescription());
        holder.binding.priceTextView.setText(String.format("$%,d", Integer.parseInt(realEstate.getPrice())));

        if (realEstate.getImageUrls() != null && !realEstate.getImageUrls().isEmpty()) {
            Bitmap bitmap = Utils.base64ToBitmap(realEstate.getImageUrls().get(0));
            if (bitmap != null) {
                Glide.with(holder.itemView.getContext()).load(bitmap).into(holder.binding.realEstateImageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return realEstates.size();
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRealestateBinding binding;

        public ViewHolder(ItemRealestateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
