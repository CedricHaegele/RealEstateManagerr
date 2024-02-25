package com.example.realestatemanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestatemanager.R;
import com.example.realestatemanager.adapter.RealEstateAdapter;
import com.example.realestatemanager.callback.OnItemClickListener;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.databinding.FragmentListBinding;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewmodel.RealtyEstateViewModel;

import java.util.List;

public class ListFragment extends Fragment implements OnItemClickListener {
    private FragmentListBinding binding;
    private RealtyEstateViewModel realtyEstateViewModel;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realtyEstateViewModel = new ViewModelProvider(requireActivity()).get(RealtyEstateViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeRealtyLists();
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

    private void observeRealtyLists() {
        realtyEstateViewModel.getRealtyLists().observe(getViewLifecycleOwner(), this::observePhotosForEachRealty);
    }

    private void observePhotosForEachRealty(List<RealEstate> properties) {
        if (properties.isEmpty()) {
            binding.messageText.setVisibility(View.VISIBLE);
        } else {
            binding.messageText.setVisibility(View.GONE);

            RealEstateAdapter adapter = new RealEstateAdapter(properties, this);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            binding.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(RealEstate realEstate) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_list_container, DetailFragment.newInstance(realEstate.getId()))
                .addToBackStack(null)
                .commit();
    }
}
