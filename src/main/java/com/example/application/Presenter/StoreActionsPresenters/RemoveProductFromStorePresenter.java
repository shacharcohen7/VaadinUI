package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;

public class RemoveProductFromStorePresenter {
    private RemoveProductFromStoreView view;
    private String userID;
    private String storeID;

    public RemoveProductFromStorePresenter(RemoveProductFromStoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
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
