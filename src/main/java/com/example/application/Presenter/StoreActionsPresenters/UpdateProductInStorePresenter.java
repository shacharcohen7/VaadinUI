package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.UpdateProductInStoreView;

public class UpdateProductInStorePresenter {
    private UpdateProductInStoreView view;
    private String userID;
    private String storeID;

    public UpdateProductInStorePresenter(UpdateProductInStoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public void onUpdateButtonClicked(String productName, int price, int quantity, String description, String category) {
        //call updateProductInStore()
        boolean success = true;
        if (success) {
            view.updateSuccess("Product was updated");
        } else {
            view.updateFailure("Invalid details.");
        }
    }
}
