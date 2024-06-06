package com.example.application.Presenter.GuestPresenters;

import com.example.application.Model.MarketModel;
import com.example.application.View.GuestViews.LoginView;

public class LoginPresenter {
    private final LoginView view;
    private final MarketModel model;

    public LoginPresenter(LoginView view, MarketModel model) {
        this.view = view;
        this.model = model;
    }

    public void onLoginButtonClicked(String username, String password) {
        if (model.login(username, password)) {
            view.loginSuccess();
        } else {
            view.loginFailure("Invalid username or password.");
        }
    }
}
