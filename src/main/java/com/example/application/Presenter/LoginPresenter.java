package com.example.application.Presenter;

import com.example.application.Model.MarketModel;
import com.example.application.View.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final MarketModel model;

    public LoginPresenter(LoginView view, MarketModel model) {
        this.view = view;
        this.model = model;
    }

    public void onLoginButtonClicked(String username, String password) {
        //send request to http and get answer
        boolean success = true;
        if (success) {
            //update user state
            view.loginSuccess();
        } else {
            view.loginFailure("Invalid username or password.");
        }
    }
}
