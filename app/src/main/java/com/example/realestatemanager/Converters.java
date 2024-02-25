package com.example.realestatemanager;

import androidx.room.TypeConverter;

import com.example.realestatemanager.model.AddressLoc;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static AddressLoc fromString(String value) {
        Type listType = new TypeToken<AddressLoc>() {
        }.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromAddressLoc(AddressLoc addressLoc) {
        Gson gson = new Gson();
        return gson.toJson(addressLoc);
    }

    @TypeConverter
    public static List<String> stringToList(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        return gson.toJson(list);
    }
}