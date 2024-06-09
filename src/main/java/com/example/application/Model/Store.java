package com.example.application.Model;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private String storeName;
    private String storeID;
    private Map<String, Product> products;

    public Store(){
        storeID = "store12";
        storeName = "ZARA";
        products = new HashMap<String, Product>();
        products.put("skirt", new Product());
    }

    public String getStoreName() {
        return storeName;
    }

    public Map<String, Product> getProducts() {
        return products;
    }
}
