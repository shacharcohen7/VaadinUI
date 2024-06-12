package com.example.application.Presenter;

import com.example.application.Model.MarketModel;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreView;

import java.util.HashMap;
import java.util.Set;

public class StorePresenter {
    private StoreView view;
    private String userID;
    private String storeID;

    public StorePresenter(StoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public String getUserName(){
        if(isMember()){
            return getMemberName();
        }
        return "Guest";
    }

    public boolean verifyStoreOwner(){
        //call verifyStoreOwner()
        return isMember();
    }

    public boolean verifyStoreManager(){
        //call verifyStoreManager()
        return isMember();
    }

    public boolean isAdmin(){
        return true;
    }

    public boolean hasInventoryPermissions(){
        //call hasInventoryPermissions()
        return isMember();
    }

    public boolean hasPurchasePermissions(){
        //call hasInventoryPermissions()
        return isMember();
    }

    public boolean isOpened(){
        //call verifyStoreOwner()
        return true;
    }

    public boolean isMember(){
        //call isMember()
        return true;
    }

    public String getMemberName(){
        //call getMemberUsername()
        return "Avi";
    }

    public String getStoreName(){
        //call getStoreName()
        return "ZARA";
    }

    public void logOut(){
        //call logout()
        MarketModel.logout();
        view.logout();
    }

    public HashMap<String, ProductDTO> getAllProducts(){
        //call getStoreProducts()
        HashMap<String, ProductDTO> allProducts = new HashMap<String, ProductDTO>();
        allProducts.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        return allProducts;
    }

    public void onSearchButtonClicked(String productName, String category,
                                      Set<String> keywords, int minPrice, int maxPrice) {
        //call inStoreSearch()
        HashMap<String, ProductDTO> productsFound = new HashMap<String, ProductDTO>();
        productsFound.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        view.showInStoreSearchResult(productsFound);
    }

    public void onAddToCartButtonClicked(ProductDTO productDto, int quantity){
        //call AddProductCart()
        boolean success = MarketModel.addToCart();
        String message;
        if (success) {
            message = "Product was added to cart";
        } else {
            message = "Failed adding product to cart";
        }
        view.addProductCartResult(message);
    }

    public void onCloseButtonClicked(){
        //call closeStore()
    }
}
