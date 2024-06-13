package com.example.application.View.GuestViews;

import com.example.application.Presenter.GuestPresenters.LoginPresenter;
import com.vaadin.flow.component.button.Button;
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

@Route("LoginView")
public class LoginView extends VerticalLayout {
    private LoginPresenter presenter;
    private String userID;
    private TextField userNameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button cancelButton;

    public LoginView() {
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new LoginPresenter(this, userID);
        userNameField = new TextField("", "username");
        passwordField = new PasswordField("", "password");
        loginButton = new Button("Login", event -> {
            presenter.onLoginButtonClicked(userNameField.getValue(), passwordField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        add(
                new H1("Log In"),
                userNameField,
                passwordField,
                new HorizontalLayout(loginButton, cancelButton)
        );
    }

    public void loginFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void loginSuccess() {
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

}
