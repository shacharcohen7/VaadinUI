package com.example.application.Presenter.GuestPresenters;

import com.example.application.Model.MarketModel;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.ProductSearchService;
import com.example.application.View.GuestViews.GuestWelcomeView;

import java.util.HashMap;
import java.util.LinkedList;

public class GuestWelcomePresenter {
    private GuestWelcomeView view;

    public GuestWelcomePresenter(GuestWelcomeView view, ProductSearchService productSearchService){
        this.view = view;
    }

    public void onSearchButtonClicked(String productName, String category, String keywords,
                                                    int minPrice, int maxPrice, int minStoreRating) {
        //send request to http and get answer
        HashMap<String, ProductDTO> productsFound = new HashMap<String, ProductDTO>();
        productsFound.put("skirt", new ProductDTO("skirt",43,"blue", "clothes"));
        ProductSearchService.setProductsFound(productsFound);
        view.showGeneralSearchResult();
    }
}
