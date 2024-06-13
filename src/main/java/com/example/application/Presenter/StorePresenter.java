package com.example.application.Presenter;

import com.example.application.Model.APIcalls;
import com.example.application.Model.MarketModel;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreView;
import com.vaadin.flow.server.VaadinSession;

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

    public boolean isStoreOwner(){
        return APIcalls.isStoreOwner(VaadinSession.getCurrent().getAttribute("memberID").toString(), storeID);
    }

    public boolean isStoreManager(){
        return APIcalls.isStoreManager(VaadinSession.getCurrent().getAttribute("memberID").toString(), storeID);
    }

    public boolean isAdmin(){
        return true;
    }

    public boolean hasInventoryPermissions(){
        return APIcalls.hasInventoryPermission(VaadinSession.getCurrent().getAttribute("memberID").toString(), storeID);
    }

    public boolean hasPurchasePermissions(){
        return APIcalls.hasPurchasePermission(VaadinSession.getCurrent().getAttribute("memberID").toString(), storeID);
    }

    public boolean isOpened(){
        //call verifyStoreOwner()
        return true;
    }

    public boolean isMember(){
        return APIcalls.isMember(userID);
    }

    public String getMemberName(){
        return APIcalls.getUser(userID).getUserName();
    }

    public String getStoreName(){
        return APIcalls.getStore(storeID).getStoreName();
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public HashMap<String, ProductDTO> getAllProducts(){
        //call getStoreProducts()
        return new HashMap<String, ProductDTO>();
    }

    public void onSearchButtonClicked(String productName, String category,
                                      Set<String> keywords, int minPrice, int maxPrice) {
        //call inStoreSearch()

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

    public boolean onCloseButtonClicked(){
        return APIcalls.closeStore(userID, storeID).contains("success");
    }
}
