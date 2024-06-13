package com.example.application.Presenter.AdminPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.AdminViews.AdminCloseStoreView;

import java.util.LinkedList;
import java.util.List;

public class AdminCloseStorePresenter {
    private AdminCloseStoreView view;
    private String userID;
    private String storeID;

    public AdminCloseStorePresenter(AdminCloseStoreView view, String userID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getAllStoreIDs(){
        //call getAllStores() and retrieve store IDs
        return new LinkedList<String>();
    }

    public void onCloseButtonClicked(String storeID) {
        if (APIcalls.closeStore(userID, storeID).contains("success")) {
            view.closeSuccess("Store was closed");
        } else {
            view.closeFailure("Invalid store ID.");
        }
    }
}
