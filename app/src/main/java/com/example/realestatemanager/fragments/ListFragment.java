package com.example.realestatemanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.R;
import com.example.realestatemanager.adapter.RealtyListAdapter;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.databinding.FragmentListBinding;
import com.example.realestatemanager.model.RealtyList;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private OnListItemSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemSelectedListener) {
            listener = (OnListItemSelectedListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnListItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener != null) {
            listener.onListFragmentDisplayed(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null) {
            listener.onListFragmentDisplayed(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupRecyclerView() {
        List<RealtyList> items = new ArrayList<>();
        items.add(new RealtyList("Villa Beach House", "4 000 000$","New York City", R.drawable.estate_image));
        items.add(new RealtyList("Love Loft", "2 000 000$","Manhattan",R.drawable.estate_image));
        items.add(new RealtyList("Lovely Appartment ", "500 000$", "Brooklyn",R.drawable.estate_image));
        items.add(new RealtyList("Manoir", "300 000$","Bronx",R.drawable.estate_image));

        RealtyListAdapter adapter = new RealtyListAdapter(items, item -> {
            navigateToDetailFragment(item);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void navigateToDetailFragment(RealtyList item) {
        if (listener != null) {
            listener.onItemSelected(item);
        }
    }

}
