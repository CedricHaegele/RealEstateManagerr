package com.example.realestatemanager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanager.R;

public class RealtyViewHolder extends RecyclerView.ViewHolder {

    public ImageView realtyImage;
    public TextView realtyTitle;
    public TextView realtyLocation;
    public TextView realtyPrice;

    public RealtyViewHolder(@NonNull View itemView) {
        super(itemView);

        realtyImage = itemView.findViewById(R.id.realty_image);
        realtyTitle = itemView.findViewById(R.id.realty_title);
        realtyLocation = itemView.findViewById(R.id.realty_location);
        realtyPrice = itemView.findViewById(R.id.realty_price);
    }
}
