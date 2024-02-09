package com.example.realestatemanager.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanager.R;

public class ListViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView, descriptionTextView;

    public ListViewHolder(View view) {
        super(view);
        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
    }
}
