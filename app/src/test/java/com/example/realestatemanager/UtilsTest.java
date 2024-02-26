package com.example.realestatemanager;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsTest {

    @Test
    public void convertEuroToDollar_isCorrect() {
        assertEquals(123, Utils.convertEuroToDollar(100));
    }

    @Test
    public void getTodayDate_isCorrect() {
        String expectedDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        assertEquals(expectedDate, Utils.getTodayDate());
    }

}

