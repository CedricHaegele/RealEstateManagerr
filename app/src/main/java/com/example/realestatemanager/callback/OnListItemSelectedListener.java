package com.example.realestatemanager.callback;

import com.example.realestatemanager.model.RealtyList;

public interface OnListItemSelectedListener {
    void onItemSelected(RealtyList item);

    void onListFragmentDisplayed(boolean displayed);
}
