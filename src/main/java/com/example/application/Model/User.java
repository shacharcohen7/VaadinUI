package com.example.application.Model;

import com.example.application.Util.ProductDTO;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private boolean isMember;
    private Map<String, Map<Product, Integer>> cart;
    private int totalPrice;

    public User(){
        username = "Avi";
        password = "password";
        isMember = false;
        cart = new HashMap<String, Map<Product, Integer>>();
        totalPrice = 0;
    }

    public boolean login(String username, String password){
        if(username.equals(this.username) && password.equals(this.password)){
            isMember = true;
            return true;
        }
        return false;
    }

    public void logout(){
        isMember = false;
    }

    public boolean isMember() {
        return isMember;
    }

    public Map<String, Map<Product, Integer>> getCart(){
        return cart;
    }

    public boolean addToCart(){
        Map<Product, Integer> productQuantity = new HashMap<Product, Integer>();
        productQuantity.put(new Product(), 1);
        cart.put("skirt", productQuantity);
        totalPrice = 43;
        return true;
    }

    public boolean removeFromCart(){
        cart.clear();
        totalPrice = 0;
        return true;
    }

    public int getTotalPrice(){
        return totalPrice;
    }
}
