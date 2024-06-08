package com.example.application.Presenter;

import com.example.application.View.StartSessionView;

public class StartSessionPresenter {
    private String userID;

    public StartSessionPresenter(StartSessionView view){
        //call enterMarketSystem() and get new userID
        userID = "user1234";
        view.startSession(userID);
    }
}
