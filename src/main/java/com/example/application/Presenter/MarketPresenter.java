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
        //call isMember()
        return true;
    }

    public String getMemberName(){
        //call getMemberUsername()
        return "Avi";
    }

    public void onSearchButtonClicked(String productName, String category,
                                                 String keywords, int minPrice, int maxPrice, int minStoreRating) {
        //call generalProductSearch()
        HashMap<String, ProductDTO> productsFound = new HashMap<String, ProductDTO>();
        productsFound.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        view.showGeneralSearchResult(productsFound);
    }

    public List<String> getAllStoresID(){
        //call getAllStores()
        List<String> storeNames = new LinkedList<String>();
        storeNames.add("store12");
        return storeNames;
    }

    public String getStoreName(String storeID){
        //call getStoreName()
        return "ZARA";
    }

    public void logOut(){
        //call logout()
        view.logout();
    }

    public void onAddToCartButtonClicked(ProductDTO productDto, int quantity){
        //call AddProductCart()
        boolean success = true;
        String message;
        if (success) {
            message = "Product was added to cart";
        } else {
            message = "Failed adding product to cart";
        }
        view.addProductCartResult(message);
    }
}
