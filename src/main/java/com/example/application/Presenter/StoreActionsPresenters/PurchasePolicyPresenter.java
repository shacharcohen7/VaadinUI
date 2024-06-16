package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.StoreActionsViews.PurchasePolicyView;

import java.util.List;

public class PurchasePolicyPresenter {
    private PurchasePolicyView view;
    private String userID;
    private String storeID;

    public PurchasePolicyPresenter(PurchasePolicyView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getStoreCurrentPurchaseRules(){
        return APIcalls.getStoreCurrentPurchaseRules(userID, storeID);
    }
}
