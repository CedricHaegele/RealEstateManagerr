package com.example.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.provider.RealEstateContentProvider;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class RealEstateContentProviderTest {

    private ContentResolver mContentResolver;

    @Before
    public void setUp() {
       Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                        AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext().getContentResolver();
    }

    @Test
    public void getRealEstatesWhenNoRealEstateInserted() {
        final Cursor cursor = mContentResolver.query(RealEstateContentProvider.URI_REAL_ESTATE, null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetRealEstate() {
        // BEFORE: Adding demo RealEstate
        final Uri realEstateUri = mContentResolver.insert(RealEstateContentProvider.URI_REAL_ESTATE, generateRealEstate());

        // TEST
        long realEstateId = ContentUris.parseId(realEstateUri);
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_REAL_ESTATE, realEstateId), null, null, null, null);

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("title")), is("Charming House"));
        cursor.close();
    }

    private ContentValues generateRealEstate() {
        final ContentValues values = new ContentValues();
        values.put("title", "Charming House");
        values.put("description", "A beautiful house with a garden");
        values.put("price", "250000");
        values.put("surface", "120");
        values.put("rooms", "5");
        values.put("bedrooms", "3");
        values.put("bathrooms", "2");
        values.put("agent", "John Doe");

        return values;
    }
}
