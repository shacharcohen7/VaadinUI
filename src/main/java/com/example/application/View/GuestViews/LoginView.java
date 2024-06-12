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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("LoginView")
public class LoginView extends VerticalLayout implements HasUrlParameter<String> {
    private LoginPresenter presenter;
    private String userID;
    private TextField userNameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button cancelButton;
    private QueryParameters userQuery;

    public LoginView() {}

    public void buildView(){
        presenter = new LoginPresenter(this, userID);
        makeUserQuery();
        userNameField = new TextField("", "username");
        passwordField = new PasswordField("", "password");
        loginButton = new Button("Login", event -> {
            presenter.onLoginButtonClicked(userNameField.getValue(), passwordField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
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
        getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
    }

    public void makeUserQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        userQuery = new QueryParameters(parameters);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        buildView();
    }
}
