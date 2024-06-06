package com.example.application.Presenter.GuestPresenters;


import com.example.application.Util.ProductDTO;
import com.example.application.View.GuestViews.GuestSearchResultView;

import java.util.HashMap;
import java.util.LinkedList;

public class GuestSearchResultPresenter {
    private GuestSearchResultView view;
    private HashMap<String, ProductDTO> productsFound;

    public GuestSearchResultPresenter(GuestSearchResultView view){
        this.view = view;
    }



}
