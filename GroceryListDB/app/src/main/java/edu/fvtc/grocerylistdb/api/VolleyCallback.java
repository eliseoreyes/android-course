package edu.fvtc.grocerylistdb.api;

import java.util.ArrayList;

import edu.fvtc.grocerylistdb.models.Product;

public interface VolleyCallback {
    void onSuccess(ArrayList<Product> result);
}
