package com.example.application.Presenter;

import com.example.application.Model.MarketModel;
import com.example.application.Model.Product;
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

    public Map<String, Map<Product, Integer>> getStoreToProductsCart(){
        //call getCart()
        return MarketModel.getCart();
    }

    public void removeProductCart(){
        //call removeProductCart()
        boolean success = MarketModel.removeFromCart();
        if (success){
            view.removeProductCartResult("Product was removed from cart");
        }
    }

    public void logOut(){
        //call logout()
        MarketModel.logout();
        view.logout();
    }

    public int getTotalPrice(){
        //call getTotalPrice()
        return MarketModel.getTotalPrice();
    }

    public void onSubmitButtonClicked(int price, String creditCard, int cvv, int month, int year, int holderID) {
        //call payWithExternalPaymentService()
        boolean success = true;
        if (success) {
            view.paymentSuccess("Payment performed successfully");
        } else {
            view.paymentFailure("Invalid payment details.");
        }
    }
}
