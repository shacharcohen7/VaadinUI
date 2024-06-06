package com.example.application.Model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MarketModel {
    private HashMap<String, StoreModel> allStores;

    public MarketModel(){

    }

    public boolean login(String username, String password){
        return true;
    }

    public HashMap<String, ProductModel> GeneralSearchResult(String productName, String category, String keywords,
                                                     int minPrice, int maxPrice, int minStoreRating){
        HashMap<String, ProductModel> productsFound = new HashMap<String, ProductModel>();
        productsFound.put("skirt", new ProductModel("skirt", "blue skirt size M", 53));
        return productsFound;
    }

    public StoreModel addStore(String storeName){
        return new StoreModel(storeName);
    }

    public void removeStore(String storeName){

    }

    public boolean signIn(String username, String birthdate, String country, String city,
                          String address, String name, String password){
        return true;
    }
}
