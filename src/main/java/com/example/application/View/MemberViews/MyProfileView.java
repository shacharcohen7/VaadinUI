package com.example.application.View.MemberViews;

import com.example.application.Presenter.GuestPresenters.SignInPresenter;
import com.example.application.Presenter.MemberPresenters.MyProfilePresenter;
import com.example.application.Util.UserDTO;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("MyProfileView")
public class MyProfileView extends VerticalLayout {
    private MyProfilePresenter presenter;
    private String userID;

    public MyProfileView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        try {
            presenter = new MyProfilePresenter(this, userID);
            UserDTO userDto = presenter.getUser();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DatePicker birthdateField = new DatePicker("birthdate", LocalDate.parse(userDto.getBirthday(), formatter));
            TextField countryField = new TextField("country", userDto.getCountry());
            TextField cityField = new TextField("city", userDto.getCity());
            TextField addressField = new TextField("address", userDto.getAddress());
            TextField nameField = new TextField("name", userDto.getName());
            Button updateButton = new Button("Save", event -> {
                presenter.onSaveButtonClicked(birthdateField.getValue(),
                        countryField.getValue(), cityField.getValue(),
                        addressField.getValue(), nameField.getValue());
            });
            Button cancelButton = new Button("Cancel", event -> {
                getUI().ifPresent(ui -> ui.navigate("MarketView"));
            });
            add(
                    new H1("My Profile:"),
                    nameField, birthdateField, countryField, cityField, addressField,
                    new HorizontalLayout(updateButton, cancelButton)
            );
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void updateSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }
}
