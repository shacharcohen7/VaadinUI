package com.example.application.Presenter;

import com.example.application.Model.MarketModel;
import com.example.application.Model.Product;
import com.example.application.Util.ProductDTO;
import com.example.application.View.MarketView;
import org.yaml.snakeyaml.error.Mark;

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
        return MarketModel.isMember();
    }

    public String getMemberName(){
        //call getMemberUsername()
        return "Avi";
    }

    public void onSearchButtonClicked(String productName, String category,
                                                 String keywords, int minPrice, int maxPrice, int minStoreRating) {
        //call generalProductSearch()
        view.showGeneralSearchResult(MarketModel.getProducts());
    }

    public List<String> getAllStoresID(){
        //call getAllStores()
        List<String> storesID = new LinkedList<String>();
        storesID.add("store12");
        return storesID;
    }

    public String getStoreName(String storeID){
        //call getStoreName()
        return "ZARA";
    }

    public void logOut(){
        //call logout()
        MarketModel.logout();
        view.logout();
    }

    public void onAddToCartButtonClicked(Product product, int quantity){
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
}
