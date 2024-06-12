package com.example.application.View.MemberViews;

import com.example.application.Presenter.GuestPresenters.SignInPresenter;
import com.example.application.Presenter.MemberPresenters.MyProfilePresenter;
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

@Route("MyProfileView")
public class MyProfileView extends VerticalLayout implements HasUrlParameter<String> {
    private MyProfilePresenter presenter;
    private QueryParameters userQuery;
    private String userID;

    public MyProfileView(){}

    public void buildView(){
        presenter = new MyProfilePresenter(this, userID);
        makeUserQuery();
        DatePicker birthdateField = new DatePicker("birthdate");
        TextField countryField = new TextField("country");
        TextField cityField = new TextField("city");
        TextField addressField = new TextField("address");
        TextField nameField = new TextField("name", "Avi", "");
        Button updateButton = new Button("Save", event -> {
            presenter.onSaveButtonClicked(birthdateField.getValue(),
                    countryField.getValue(), cityField.getValue(),
                    addressField.getValue(), nameField.getValue());
        });
        Button cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
        });
        add(
                new H1("My Profile:"),
                nameField, birthdateField, countryField, cityField, addressField,
                new HorizontalLayout(updateButton, cancelButton)
        );
    }

    public void updateFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void updateSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
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
