package com.example.application.Presenter.GuestPresenters;

import com.example.application.Model.MarketModel;
import com.example.application.View.GuestViews.GuestWelcomeView;

public class GuestWelcomePresenter {
    private GuestWelcomeView view;
    private MarketModel model;

    public GuestWelcomePresenter(GuestWelcomeView view, MarketModel model){
        this.view = view;
        this.model = model;
    }

    public void onSearchButtonClicked(String productName, String category, String keywords,
                                                    int minPrice, int maxPrice, int minStoreRating) {
        view.showGeneralSearchResult(model.GeneralSearchResult(productName, category, keywords,
                minPrice, maxPrice, minStoreRating));
    }
}
