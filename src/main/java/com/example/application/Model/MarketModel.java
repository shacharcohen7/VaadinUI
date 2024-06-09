package com.example.application.Model;

import java.util.Map;

public class MarketModel {
    private static User user = new User();
    private static Store store = new Store();
    private Product product = new Product();

    public static boolean login(String username, String password){
        return user.login(username, password);
    }

    public static void logout(){
        user.logout();
    }

    public static boolean isMember(){
        return user.isMember();
    }

    public static String getStoreName() {
        return store.getStoreName();
    }

    public static Map<String, Product> getProducts() {
        return store.getProducts();
    }

    public static boolean addToCart(){
        return user.addToCart();
    }

    public static boolean removeFromCart(){
        return user.removeFromCart();
    }

    public static Map<String, Map<Product, Integer>> getCart(){
        return user.getCart();
    }

    public static int getTotalPrice(){
        return user.getTotalPrice();
    }
}
