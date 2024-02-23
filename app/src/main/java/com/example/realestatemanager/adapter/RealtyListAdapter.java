package com.example.realestatemanager.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.realestatemanager.model.CombinedRealtyData;
import com.example.realestatemanager.model.RealtyList;

import java.util.List;

public class RealtyListAdapter extends RecyclerView.Adapter<RealtyListAdapter.ViewHolder> {
    private List<CombinedRealtyData> items;
    private OnItemClickListener onClickListener;

    public RealtyListAdapter(List<CombinedRealtyData> items, OnItemClickListener onClickListener) {
        this.items = items;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.realty_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CombinedRealtyData data = items.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPropertyPhoto;
        TextView textViewTitle, textViewAddress, textViewPrice;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewPropertyPhoto = itemView.findViewById(R.id.imageViewPropertyPhoto);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CombinedRealtyData data = items.get(position);
                    onClickListener.onItemClick(data);
                }
            });
        }

        void bind(CombinedRealtyData data) {
            textViewTitle.setText(data.getRealtyList().getTitle());
            textViewAddress.setText(data.getRealtyList().getAddress());
            textViewPrice.setText(data.getRealtyList().getPrice());

            if (!data.getPhotos().isEmpty() && data.getPhotos().get(0).getImageUri() != null) {
                // Convertir le tableau de bytes en Bitmap
                // Bitmap bitmap = BitmapFactory.decodeByteArray(data.getPhotos().get(0).getImageUri(), 0, data.getPhotos().get(0).getImageUri().length());

                // Utiliser Glide pour charger le Bitmap
                // Glide.with(itemView.getContext())
                //    .load(bitmap)
                //    .error(R.drawable.estate_image)
                //   .into(imageViewPropertyPhoto);
            } else {
                imageViewPropertyPhoto.setImageResource(R.drawable.estate_image);
            }
        }
    }
}
