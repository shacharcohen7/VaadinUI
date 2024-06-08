package com.example.application.Presenter;

import com.example.application.Util.ProductDTO;
import com.example.application.View.MarketView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MarketPresenter {
    private MarketView view;
    private String userID;

    public MarketPresenter(MarketView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public String getUserName(){
        if(isMember()){
            return getMemberName();
        }
        return "Guest";
    }

    public boolean isMember(){
        //send request to http and get answer
        return false;
    }

    public String getMemberName(){
        //send request to http and get answer
        return "Avi";
    }

    public void onSearchButtonClicked(String productName, String category,
                                                 String keywords, int minPrice, int maxPrice, int minStoreRating) {
        //send request to http and get answer
        HashMap<String, ProductDTO> productsFound = new HashMap<String, ProductDTO>();
        productsFound.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        view.showGeneralSearchResult(productsFound);
    }

    public List<String> getAllStoreNames(){
        //send request to http and get answer
        List<String> storeNames = new LinkedList<String>();
        storeNames.add("ZARA");
        storeNames.add("Renuar");
        return storeNames;
    }

    public void onStoreButtonClicked(String storeName){
        view.openStore(storeName);
    }
}
