package com.example.application.Presenter;

import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreView;

import java.util.HashMap;

public class StorePresenter {
    private StoreView view;
    private String userID;

    public StorePresenter(StoreView view, String userID){
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

    public HashMap<String, ProductDTO> getAllProducts(){
        //send request to http and get answer
        HashMap<String, ProductDTO> allProducts = new HashMap<String, ProductDTO>();
        allProducts.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        return allProducts;
    }

    public void onSearchButtonClicked(String productName, String category,
                                      String keywords, int minPrice, int maxPrice) {
        //send request to http and get answer
        HashMap<String, ProductDTO> productsFound = new HashMap<String, ProductDTO>();
        productsFound.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        view.showInStoreSearchResult(productsFound);
    }
}
