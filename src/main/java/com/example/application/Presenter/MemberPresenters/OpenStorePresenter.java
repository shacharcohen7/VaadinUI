package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.MemberViews.OpenStoreView;

public class OpenStorePresenter {
    private OpenStoreView view;
    private String userID;

    public OpenStorePresenter(OpenStoreView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public void onDoneButtonClicked(String storeName, String description) {
        if(APIcalls.openStore(userID, storeName, description) != null){
            view.openSuccess("Store was opened");
        }
        else{
            view.openFailure("Invalid input");
        }
    }
}
