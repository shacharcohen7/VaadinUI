package com.example.application.Presenter;

import com.example.application.View.StartSessionView;

public class StartSessionPresenter {
    private StartSessionView view;
    private String userID;

    public StartSessionPresenter(StartSessionView view){
        this.view = view;
        getNewUserID();
        view.startSession(userID);
    }

    public void getNewUserID(){
        //send request to http and get answer
        userID = "user1234";
    }
}
