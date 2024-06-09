package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.MarketModel;
import com.example.application.View.MemberViews.OpenStoreView;
import com.example.application.View.StoreActionsViews.AddProductToStoreView;

public class AddProductToStorePresenter {
    private AddProductToStoreView view;
    private String userID;
    private String storeID;

    public AddProductToStorePresenter(AddProductToStoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public void onAddButtonClicked(String productName, int price, int quantity, String description, String category) {
        //call addProductToStore()
        boolean success = true;
        if (success) {
            view.addSuccess("Product was added");
        } else {
            view.addFailure("Invalid details.");
        }
    }
}
