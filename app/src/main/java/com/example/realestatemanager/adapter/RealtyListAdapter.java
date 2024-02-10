package com.example.realestatemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.realestatemanager.R;
import com.example.realestatemanager.callback.OnItemClickListener;
import com.example.realestatemanager.model.RealtyList;

import java.util.List;
import java.util.function.Consumer;

public class RealtyListAdapter extends RecyclerView.Adapter<RealtyListAdapter.ViewHolder> {
    private List<RealtyList> items;
    private OnItemClickListener onClickListener;

    public RealtyListAdapter(List<RealtyList> items, OnItemClickListener onClickListener) {
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
        holder.bind(realtyList);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewPropertyPhoto;
        public TextView textViewTitle, textViewAddress, textViewPrice;

        public ViewHolder(View itemView, OnItemClickListener onClickListener) {
            super(itemView);
            imageViewPropertyPhoto = itemView.findViewById(R.id.imageViewPropertyPhoto);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onItemClick(items.get(position));
                    }
                }
            });
        }

        public void bind(RealtyList realtyList) {
            textViewTitle.setText(realtyList.getTitle());
            textViewAddress.setText(realtyList.getAddress());
            textViewPrice.setText(realtyList.getPrice());

            Glide.with(itemView.getContext())
                    .load(realtyList.getImageUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.estate_image).error(R.drawable.estate_image))
                    .into(imageViewPropertyPhoto);

        }
    }
}
