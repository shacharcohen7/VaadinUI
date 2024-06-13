package com.example.application.Presenter.GuestPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Model.MarketModel;
import com.example.application.View.GuestViews.LoginView;
import com.vaadin.flow.server.VaadinSession;

public class LoginPresenter {
    private LoginView view;
    private String userID;

    public LoginPresenter(LoginView view, String userID) {
        this.view = view;
        this.userID = userID;
    }

    public void onLoginButtonClicked(String username, String password) {
        String memberID = APIcalls.login(userID, username, password);
        if (memberID != null) {
            VaadinSession.getCurrent().setAttribute("memberID", memberID);
            view.loginSuccess();
        } else {
            view.loginFailure("Invalid username or password.");
        }
    }
}
