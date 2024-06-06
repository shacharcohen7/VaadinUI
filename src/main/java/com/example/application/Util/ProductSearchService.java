package com.example.application.Util;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductSearchService {
    public static Map<String, ProductDTO> productsFound = new HashMap<>();

    public static void setProductsFound(Map<String, ProductDTO> newProductsFound) {
        productsFound = newProductsFound;
    }

    public static Map<String, ProductDTO> getProductsFound() {
        return productsFound;
    }
}

