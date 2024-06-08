package com.example.application.Presenter;

import com.example.application.Util.ProductDTO;
import com.example.application.View.ShoppingCartView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShoppingCartPresenter {
    private ShoppingCartView view;
    private String userID;

    public ShoppingCartPresenter(ShoppingCartView view, String userID){
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

    public String getStoreName(String storeID){
        //call getStoreName()
        return "ZARA";
    }

    public Map<String, Map<ProductDTO, Integer>> getStoreToProductsCart(){
        //call getCart()
        Map<String, Map<ProductDTO, Integer>> storeToProductsCart = new HashMap<String, Map<ProductDTO, Integer>>();
        Map<ProductDTO, Integer> storeProducts = new HashMap<ProductDTO, Integer>();
        storeProducts.put(new ProductDTO("skirt",43,"blue", "clothes"), 2);
        storeToProductsCart.put("ZARA", storeProducts);
        return storeToProductsCart;
    }

    public void removeProductCart(){
        //call removeProductCart()
        boolean success = true;
        if (success){
            view.removeProductCartResult("Product was removed from cart");
        }
    }

    public void logOut(){
        //call logout()
        view.logout();
    }

    public int getTotalPrice(){
        //call getTotalPrice()
        return 43;
    }
}
