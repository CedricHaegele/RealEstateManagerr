package com.example.realestatemanager.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.realestatemanager.R;
import com.example.realestatemanager.Utils;
import com.example.realestatemanager.callback.OnItemClickListener;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewholder.PropertyViewHolder;

import java.util.List;

public class RealEstateAdapter extends RecyclerView.Adapter<PropertyViewHolder> {
    private final List<RealEstate> properties;
    private final OnItemClickListener listener;

    public RealEstateAdapter(List<RealEstate> properties, OnItemClickListener listener) {
        this.properties = properties;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.realty_list_item, parent, false);
        return new PropertyViewHolder(view, properties, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        RealEstate realEstate = properties.get(position);
        holder.textViewTitle.setText(realEstate.getTitle());
        if (realEstate.getAddressLoc() != null) {
            holder.textViewAddress.setText(realEstate.getAddressLoc().getAddressLabel());
        }
        holder.textViewPrice.setText(String.valueOf(realEstate.getPrice()));
        if (realEstate.getImageUrls() != null && !realEstate.getImageUrls().isEmpty()) {
            Bitmap bitmap = Utils.base64ToBitmap(realEstate.getImageUrls().get(0));
            if (bitmap != null) {
                Glide.with(holder.itemView.getContext()).load(bitmap).into(holder.imageViewPropertyPhoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}
