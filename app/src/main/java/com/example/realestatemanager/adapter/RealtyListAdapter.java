package com.example.realestatemanager.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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

            if (realtyList.getImageUrl() != null && !realtyList.getImageUrl().isEmpty()) {
                Uri imageUri = Uri.parse(realtyList.getImageUrl()); // Convertir la chaîne en Uri
                Glide.with(itemView.getContext())
                        .load(imageUri)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("GlideError", "Erreur de chargement", e);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("GlideSuccess", "Image chargée avec succès");
                                return false;
                            }
                        })
                        .into(imageViewPropertyPhoto);
            } else {
                Log.d("ImageLoading", "L'URI de l'image est null ou vide.");
            }
        }

    }
}

