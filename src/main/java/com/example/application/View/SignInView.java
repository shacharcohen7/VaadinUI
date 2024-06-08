package com.example.application.View;

import com.example.application.Model.MarketModel;
import com.example.application.Presenter.SignInPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("SignInView")
public class SignInView extends VerticalLayout implements HasUrlParameter<String> {
    private SignInPresenter presenter;
    private String userID;
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
