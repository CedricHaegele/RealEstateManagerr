package com.example.realestatemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatemanager.R;
import com.example.realestatemanager.model.ItemList;
import com.example.realestatemanager.viewholder.ListViewHolder;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ItemList item);
    }

    private List<ItemList> list;
    private OnItemClickListener listener;

    public ListAdapter(List<ItemList> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemList item = list.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
