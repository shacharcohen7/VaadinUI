package com.example.application.View.GuestViews;

import com.example.application.Model.MarketModel;
import com.example.application.Presenter.GuestPresenters.LoginPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("LoginView")
public class LoginView extends VerticalLayout {
    private LoginPresenter presenter;
    private TextField userNameField;
    private TextField passwordField;
    private Button loginButton;

    public LoginView() {
        presenter = new LoginPresenter(this, new MarketModel());
        userNameField = new TextField("", "username");
        passwordField = new TextField("", "password");
        loginButton = new Button("Login", event -> {
            presenter.onLoginButtonClicked(userNameField.getValue(), passwordField.getValue());
        });
        add(
                new H1("Log In"),
                userNameField,
                passwordField,
                loginButton
        );
    }

    public void loginFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void loginSuccess() {
        getUI().ifPresent(ui -> ui.navigate("WelcomeMemberView"));
    }
}
