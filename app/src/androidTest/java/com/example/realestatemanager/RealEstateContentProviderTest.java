package com.example.realestatemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.provider.ProviderTestRule;

import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.provider.RealEstateContentProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class RealEstateContentProviderTest {

    @Rule
    public ProviderTestRule providerTestRule = new ProviderTestRule.Builder(RealEstateContentProvider.class, RealEstateContentProvider.AUTHORITY).build();

    private Context context;
    private AppDatabase testDb;
    private Uri lastInsertedUri;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        AppDatabase.setTestInstance(testDb);
    }

    private void insertTestProperty() { // Changed to void
        final ContentValues values = new ContentValues();
        values.put("title", "Test Property");
        values.put("price", "250000");

        lastInsertedUri = providerTestRule.getResolver().insert(RealEstateContentProvider.URI_REAL_ESTATE, values);
        assertNotNull("Uri should not be null after insertion", lastInsertedUri);
    }

    @Test
    public void testInsertAndQuery() {
        insertTestProperty();

        Cursor cursor = providerTestRule.getResolver().query(lastInsertedUri, null, null, null, null);
        assertNotNull("Cursor should not be null", cursor);
        assertTrue("Cursor should contain at least one result", cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void testDelete() {
        insertTestProperty();

        int count = providerTestRule.getResolver().delete(lastInsertedUri, null, null);
        assertEquals("Delete operation failed", 1, count);
    }

    @Test
    public void testUpdate() {
        insertTestProperty();

        ContentValues values = new ContentValues();
        values.put("price", "300000");

        int count = providerTestRule.getResolver().update(lastInsertedUri, values, null, null);
        assertEquals("Update operation failed", 1, count);
    }
}
