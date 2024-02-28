package com.example.realestatemanager;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.realestatemanager.model.AddressLoc;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.repository.RealEstateRepository;
import com.example.realestatemanager.viewmodel.RealEstateViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class RealEstateViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Application mockApplication;

    @Mock
    private RealEstateRepository realEstateRepository;

    @Mock
    private Observer<List<RealEstate>> observer;

    private RealEstateViewModel viewModel;

    private List<RealEstate> expectedList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new RealEstateViewModel(mockApplication);

        expectedList = new ArrayList<>();
        expectedList.add(new RealEstate(
                1, // id
                "Charming Apartment", // title
                "A spacious and charming apartment in the heart of the city.", // description
                "250000", // price
                "120", // surface
                "5", // rooms
                "3", // bedrooms
                "2", // bathrooms
                new AddressLoc(0, new LatLng(40.7128, -74.0060), "123 Main Street"), // addressLoc
                "John Doe", // agent
                "2023-01-01", // availableDate
                new ArrayList<>(Arrays.asList("url1", "url2")), // imageUrls
                new ArrayList<>(Arrays.asList("School", "Shopping")), // pointsOfInterest
                "2023-01-01", // dateAdded
                "true", // hasSchoolNearby
                "true", // hasShoppingNearby
                "Available", // status
                new Date(), // marketDate
                null // soldDate
        ));

        MutableLiveData<List<RealEstate>> liveDataList = new MutableLiveData<>();
        liveDataList.setValue(expectedList);
        when(realEstateRepository.getAll()).thenReturn(liveDataList);
        viewModel.getRealtyLists().observeForever(observer);
    }

    @Test
    public void loadRealtyList_loadsCorrectly() {
        // Maintenant, expectedList est accessible ici
        verify(observer).onChanged(expectedList);
    }
}