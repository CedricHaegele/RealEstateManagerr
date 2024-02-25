package com.example.realestatemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanager.R;
import com.example.realestatemanager.model.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<RealEstate> realEstates = new ArrayList<>();

    // Constructeur
    public SearchResultsAdapter() {
    }

    // Mettre à jour les données
    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_realestate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealEstate realEstate = realEstates.get(position);

    }

    @Override
    public int getItemCount() {
        return realEstates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);
            // Initialisez vos vues ici
            // Par exemple: textViewTitle = view.findViewById(R.id.textViewTitle);
        }
    }
}

