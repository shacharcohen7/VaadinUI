package com.example.application.View.GuestViews;

import com.example.application.Presenter.GuestPresenters.SignInPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("SignInView")
public class SignInView extends VerticalLayout {
    private SignInPresenter presenter;
    private String userID;
    private TextField userNameField;
    private DatePicker birthdateField;
    private TextField countryField;
    private TextField cityField;
    private TextField addressField;
    private TextField nameField;
    private PasswordField passwordField;
    private Button signInButton;
    private Button cancelButton;

    public SignInView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new SignInPresenter(this, userID);
        userNameField = new TextField("username");
        birthdateField = new DatePicker("birthdate");
        countryField = new TextField("country");
        cityField = new TextField("city");
        addressField = new TextField("address");
        nameField = new TextField("name");
        passwordField = new PasswordField("password");
        signInButton = new Button("Sign In", event -> {
            presenter.onSignInButtonClicked(userNameField.getValue(), birthdateField.getValue(),
                    countryField.getValue(), cityField.getValue(),
                    addressField.getValue(), nameField.getValue(),
                    passwordField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        add(
                new H1("Sign In"),
                new HorizontalLayout(nameField, birthdateField, countryField, cityField),
                new HorizontalLayout(addressField, userNameField, passwordField),
                new HorizontalLayout(signInButton, cancelButton)
        );
    }

    public void SignInFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void SignInSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }
}
