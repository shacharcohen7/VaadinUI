package com.example.application.Model;

public class Product {
    private String productName;
    private String category;
    private String description;
    private int price;

    public Product(){
        productName = "skirt";
        category = "clothes";
        description = "blue";
        price = 43;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
