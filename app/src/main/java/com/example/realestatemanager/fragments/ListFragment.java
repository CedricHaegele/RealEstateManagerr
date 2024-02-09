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

import com.example.realestatemanager.adapter.ListAdapter;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.databinding.FragmentListBinding;
import com.example.realestatemanager.model.ItemList;

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
        List<ItemList> items = new ArrayList<>();
        items.add(new ItemList("Title 1", "Description 1"));
        items.add(new ItemList("Title 2", "Description 2"));
        items.add(new ItemList("Title 3", "Description 3"));
        items.add(new ItemList("Title 4", "Description 4"));

        ListAdapter adapter = new ListAdapter(items, item -> {
            navigateToDetailFragment(item);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
    }

    private void navigateToDetailFragment(ItemList item) {
        Log.d("tagii", "navigateToDetailFragment: " + item);
        Log.d("tagii", "listener: " + listener);
        if (listener != null) {
            listener.onItemSelected(item);
        }
    }
}
