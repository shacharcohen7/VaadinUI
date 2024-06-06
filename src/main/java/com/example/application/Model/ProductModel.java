package com.example.application.Model;

public class ProductModel {
    private String productName;
    private String description;
    private int price;

    public ProductModel(String productName, String description, int price){
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public String getProductName(){
        return this.productName;
    }

    public String getDescription(){
        return this.description;
    }

    public int getPrice(){
        return this.price;
    }
}
