package com.example.application.Presenter.MemberPresenter;

import com.example.application.View.MemberViews.OpenStoreView;
import com.example.application.View.SignInView;

import java.time.LocalDate;

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
