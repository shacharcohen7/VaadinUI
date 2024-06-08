package com.example.application.View;

import com.example.application.Model.MarketModel;
import com.example.application.Presenter.SignInPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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

@Route("SignInView")
public class SignInView extends VerticalLayout implements HasUrlParameter<String> {
    private SignInPresenter presenter;
    private QueryParameters userQuery;
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

    public SignInView(){}

    public void buildView(){
        presenter = new SignInPresenter(this, userID);
        makeUserQuery();
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
                    userNameField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
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

    public void SignInSuccess() {
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
