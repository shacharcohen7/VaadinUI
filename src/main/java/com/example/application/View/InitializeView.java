package com.example.application.View;

import com.example.application.Presenter.InitializePresenter;
import com.example.application.Util.PaymentServiceDTO;
import com.example.application.Util.SupplyServiceDTO;
import com.example.application.Util.UserDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Route("")
public class InitializeView extends VerticalLayout {
    InitializePresenter presenter;

    public InitializeView(){
        presenter = new InitializePresenter(this);
    }

    public void startSession(){
        UI.getCurrent().access(() -> getUI().ifPresent(ui -> ui.navigate("StartSessionView")));
    }

    public void startMarket(){
        UI.getCurrent().access(() -> getUI().ifPresent(ui -> ui.navigate("MarketView")));
    }

    public void buildView(){
        VerticalLayout userLayout = new VerticalLayout();
        userLayout.add(new H1("User Details:"));
        TextField userNameField = new TextField("username");
        DatePicker birthdateField = new DatePicker("birthdate");
        TextField countryField = new TextField("country");
        TextField cityField = new TextField("city");
        TextField addressField = new TextField("address");
        TextField nameField = new TextField("name");
        PasswordField passwordField = new PasswordField("password");
        userLayout.add(new HorizontalLayout(userNameField, birthdateField, countryField),
                new HorizontalLayout(cityField, addressField, nameField, passwordField));

        VerticalLayout paymentServiceLayout = new VerticalLayout();
        paymentServiceLayout.add(new H1("Payment Service:"));
        TextField paymentDealerNumberField = new TextField("licensed dealer number");
        TextField paymentServiceNameField = new TextField("payment service name");
        TextField urlField = new TextField("url");
        paymentServiceLayout.add(paymentDealerNumberField, paymentServiceNameField, urlField);

        VerticalLayout supplyServiceLayout = new VerticalLayout();
        paymentServiceLayout.add(new H1("Payment Service:"));
        TextField supplyDealerNumberField = new TextField("licensed dealer number");
        TextField supplyServiceName = new TextField("supply service name");
        TextField countries = new TextField("countries");
        TextField cities = new TextField("cities");
        supplyServiceLayout.add(supplyDealerNumberField, supplyServiceName, countries, cities);

        add(userLayout, paymentServiceLayout, supplyServiceLayout,
                new Button("Initialize", event -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    UserDTO userDTO = new UserDTO("", userNameField.getValue(), birthdateField.getValue().format(formatter),
                            countryField.getValue(), cityField.getValue(), addressField.getValue(), nameField.getValue());
                    PaymentServiceDTO paymentServiceDTO = new PaymentServiceDTO(paymentDealerNumberField.getValue(),
                            paymentServiceNameField.getValue(), urlField.getValue());
                    presenter.onInitializeButtonClicked(userDTO, passwordField.getValue(), paymentServiceDTO, supplyDealerNumberField.getValue(),
                            supplyServiceName.getValue(), countries.getValue(), cities.getValue());
                })
        );
    }
}
