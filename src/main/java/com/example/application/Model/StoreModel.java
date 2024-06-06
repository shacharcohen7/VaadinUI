package com.example.application.Model;

import java.util.HashMap;
import java.util.LinkedList;

public class StoreModel {
    private String storeName;
    private HashMap<String, ProductModel> storeProducts;

    public StoreModel(String storeName){
        this.storeName = storeName;
        this.storeProducts = new HashMap<String, ProductModel>();
    }

    public void addStoreProduct(ProductModel product){
        storeProducts.put(product.getProductName(), product);
    }

    public void removeStoreProduct(String productName){
        storeProducts.remove(productName);
    }
}
