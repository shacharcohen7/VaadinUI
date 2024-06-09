package com.example.application.Presenter.MemberPresenters;

import com.example.application.View.MemberViews.OpenStoreView;

public class OpenStorePresenter {
    private OpenStoreView view;
    private String userID;

    public OpenStorePresenter(OpenStoreView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public void onDoneButtonClicked(String storeName, String description) {
        //call openStore()
        view.open("Store was opened");
    }
}
