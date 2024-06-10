package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RemoveProductFromStorePresenter {
    private RemoveProductFromStoreView view;
    private String userID;
    private String storeID;

    public RemoveProductFromStorePresenter(RemoveProductFromStoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getAllProductNames(){
        //call getStoreProducts() and retrieve product names
        return new LinkedList<String>();
    }

    public void onRemoveButtonClicked(String productName) {
        //call removeProductFromStore()
        boolean success = true;
        if (success) {
            view.removeSuccess("Product was removed");
        } else {
            view.removeFailure("Invalid details.");
        }
    }
}