package com.example.realestatemanager.callback;

import com.example.realestatemanager.model.ItemList;

public interface OnListItemSelectedListener {
    void onItemSelected(ItemList item);

    void onListFragmentDisplayed(boolean displayed);
}
