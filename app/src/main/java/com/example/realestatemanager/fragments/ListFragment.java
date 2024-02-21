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
import com.example.realestatemanager.adapter.RealtyListAdapter;
import com.example.realestatemanager.callback.OnItemClickListener;
import com.example.realestatemanager.callback.OnListItemSelectedListener;
import com.example.realestatemanager.databinding.FragmentListBinding;
import com.example.realestatemanager.model.CombinedRealtyData;
import com.example.realestatemanager.model.Photo;
import com.example.realestatemanager.model.RealtyList;
import com.example.realestatemanager.viewmodel.RealtyListViewModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    private RealtyListViewModel realtyListViewModel;
    private OnListItemSelectedListener listener;
    private Map<Integer, List<Photo>> realtyPhotosMap = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        realtyListViewModel = new ViewModelProvider(requireActivity()).get(RealtyListViewModel.class);
        observeRealtyLists();
    }

    private void observeRealtyLists() {
        realtyListViewModel.getRealtyLists().observe(getViewLifecycleOwner(), this::observePhotosForEachRealty);
    }

    private void observePhotosForEachRealty(List<RealtyList> realtyLists) {
        final AtomicInteger realtyCount = new AtomicInteger(realtyLists.size());
        final List<CombinedRealtyData> combinedDataList = new ArrayList<>();

        if (realtyLists.isEmpty()) {
            updateUI(combinedDataList);
            return;
        }

        for (RealtyList realty : realtyLists) {
            realtyListViewModel.getPhotosByPropertyId(realty.getId()).observe(getViewLifecycleOwner(), photos -> {
                combinedDataList.add(new CombinedRealtyData(realty, photos));

                if (realtyCount.decrementAndGet() == 0) {
                    updateUI(combinedDataList);
                }
            });
        }
    }

    private void updateUI(List<CombinedRealtyData> combinedDataList) {

        RealtyListAdapter adapter = new RealtyListAdapter(combinedDataList, item -> {
            if (listener != null) {
                listener.onItemSelected(item.getRealtyList());
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
            throw new RuntimeException(context.toString() + " must implement OnListItemSelectedListener");
        }
    }
}
