package com.example.application.Presenter.GuestPresenters;

import com.example.application.View.GuestViews.SignInView;

import java.time.LocalDate;

public class SignInPresenter {
    private SignInView view;
    private String userID;

    public SignInPresenter(SignInView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public void onSignInButtonClicked(String username, LocalDate birthdate, String country, String city,
                                      String address, String name, String password) {
        //call register()
        boolean success = true;
        if (success){
            view.SignInSuccess("Registration performed");
        } else {
            view.SignInFailure("Sign in failed");
        }
    }
}
