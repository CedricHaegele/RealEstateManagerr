package com.example.realestatemanager;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

// This class uses JUnit4 to run instrumentation tests on Android code.
@RunWith(AndroidJUnit4.class)
@SmallTest // Marks this as a small test, implying it runs in milliseconds
public class NetworkConnectionTest {

    private Context context; // Holds the application context

    @Before
    // Setup method that runs before each test method
    public void setUp() {
        // Initializes the context with the application context before each test
        context = ApplicationProvider.getApplicationContext();
    }

    @Test // Annotation indicating this is a test method
    public void isInternetAvailable_returnsTrue() {
        // Asserts that the Utils.isInternetAvailable method returns true
        // It's expected that this test runs in an environment where internet is available
        assertTrue(Utils.isInternetAvailable(context));
    }
}
