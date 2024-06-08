package com.example.application.Presenter;

import com.example.application.Model.MarketModel;
import com.example.application.View.LoginView;

public class LoginPresenter {
    private LoginView view;
    private String userID;

    public LoginPresenter(LoginView view, String userID) {
        this.view = view;
        this.userID = userID;
    }

    public void onLoginButtonClicked(String username, String password) {
        //call login()
        boolean success = true;
        if (success) {
            view.loginSuccess();
        } else {
            view.loginFailure("Invalid username or password.");
        }
    }
}
