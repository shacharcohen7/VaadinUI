package com.example.application.Presenter.GuestPresenters;

import com.example.application.Model.MarketModel;
import com.example.application.View.GuestViews.SignInView;

public class SignInPresenter {
    private final SignInView view;
    private final MarketModel model;

    public SignInPresenter(SignInView view, MarketModel model){
        this.view = view;
        this.model = model;
    }

    public void onSignInButtonClicked(String username, String birthdate, String country, String city,
                                                                    String address, String name, String password) {
        if(model.signIn(username, birthdate, country, city, address, name, password)){
            view.SignInSuccess();
        } else {
            view.SignInFailure("Sign in failed");
        }
    }
}
