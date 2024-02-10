package com.example.realestatemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realestatemanager.R;
import com.example.realestatemanager.model.RealtyList;
import java.util.List;
import java.util.function.Consumer;

public class RealtyListAdapter extends RecyclerView.Adapter<RealtyListAdapter.ViewHolder> {

    private List<RealtyList> items;
    private Consumer<RealtyList> onClickListener;

    public RealtyListAdapter(List<RealtyList> items, Consumer<RealtyList> onClickListener) {
        this.items = items;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.realty_list_item, parent, false);
        return new ViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealtyList realtyList = items.get(position);
        holder.textViewTitle.setText(realtyList.getTitle());
        holder.textViewAddress.setText(realtyList.getAddress());
        holder.textViewPrice.setText(realtyList.getPrice());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewPropertyPhoto;
        public TextView textViewTitle, textViewAddress, textViewPrice;
        private Consumer<RealtyList> onClickListener;

        public ViewHolder(View itemView, Consumer<RealtyList> onClickListener) {
            super(itemView);
            imageViewPropertyPhoto = itemView.findViewById(R.id.imageViewPropertyPhoto);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(v -> {

            });
        }
    }}

