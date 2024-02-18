package com.example.realestatemanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.realestatemanager.viewmodel.RealtyListViewModel;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    private RealtyListViewModel realtyListViewModel;
    private OnListItemSelectedListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtenez l'instance de RealtyListViewModel
        realtyListViewModel = new ViewModelProvider(this).get(RealtyListViewModel.class);

        // Observez les LiveData de RealtyList et mettez à jour l'UI lorsque les données changent
        realtyListViewModel.getRealtyLists().observe(getViewLifecycleOwner(), realtyLists -> {
            updateUI(realtyLists);
        });
    }

    private void updateUI(List<RealtyList> items) {

        RealtyListAdapter adapter = new RealtyListAdapter(items, item -> {
            if (listener != null) {
                listener.onItemSelected(item);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListItemSelectedListener) {
            listener = (OnListItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListItemSelectedListener");
        }
    }
}
