package com.example.application.View.GuestViews;

import com.example.application.Model.MarketModel;
import com.example.application.Presenter.GuestPresenters.SignInPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("SignInView")
public class SignInView extends VerticalLayout {
    private SignInPresenter presenter;
    private TextField userNameField;
    private TextField birthdateField;
    private TextField countryField;
    private TextField cityField;
    private TextField addressField;
    private TextField nameField;
    private TextField passwordField;
    private Button signInButton;

    public SignInView(){
        presenter = new SignInPresenter(this, new MarketModel());
        userNameField = new TextField("", "username");
        birthdateField = new TextField("", "birthdate");
        countryField = new TextField("", "country");
        cityField = new TextField("", "city");
        addressField = new TextField("", "address");
        nameField = new TextField("", "name");
        passwordField = new TextField("", "password");
        signInButton = new Button("Sign In", event -> {
            presenter.onSignInButtonClicked(userNameField.getValue(), birthdateField.getValue(),
                    countryField.getValue(), cityField.getValue(),
                    addressField.getValue(), nameField.getValue(),
                    userNameField.getValue());
        });
        add(
                new H1("Sign In"),
                userNameField,
                birthdateField,
                countryField,
                cityField,
                addressField,
                nameField,
                passwordField,
                signInButton
        );
    }

    public void SignInFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void SignInSuccess() {
        getUI().ifPresent(ui -> ui.navigate(""));
    }
}
