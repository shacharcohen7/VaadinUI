package com.example.application.View;

import com.example.application.Model.MarketModel;
import com.example.application.Presenter.LoginPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("LoginView")
public class LoginView extends VerticalLayout implements HasUrlParameter<String> {
    private LoginPresenter presenter;
    private String userID;
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
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        QueryParameters queryParameters = new QueryParameters(parameters);
        getUI().ifPresent(ui -> ui.navigate("WelcomeView", queryParameters));
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
    }
}
