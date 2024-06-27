package com.example.application.View;

import com.example.application.Presenter.GuestPresenters.SignInPresenter;
import com.example.application.Presenter.PaymentPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("PaymentView")
public class PaymentView extends VerticalLayout {

    private PaymentPresenter presenter;
    private String userID;
    private TextField holderIDField;
    private TextField creditCardField;
    private ComboBox<Integer> yearComboBox;
    private ComboBox<Integer> monthComboBox;
    private IntegerField cvvField;
    private Button submitButton;
    private Button cancelButton;

    public PaymentView() {
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    private void buildView() {
        presenter = new PaymentPresenter(this, userID);

        holderIDField = new TextField("Holder ID");
        holderIDField.setWidth("300px");

        creditCardField = new TextField("Credit Card");
        creditCardField.setWidth("300px");

        yearComboBox = new ComboBox<>("Year");
        yearComboBox.setItems(2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032);
        yearComboBox.setWidth("300px");

        monthComboBox = new ComboBox<>("Month");
        monthComboBox.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        monthComboBox.setWidth("300px");

        cvvField = new IntegerField("CVV");
        cvvField.setWidth("300px");

        submitButton = new Button("Submit", event -> {
            presenter.onSubmitButtonClicked(
                    creditCardField.getValue(),
                    cvvField.getValue(),
                    monthComboBox.getValue(),
                    yearComboBox.getValue(),
                    holderIDField.getValue()
            );
        });
        submitButton.getStyle().set("background-color", "#e91e63").set("color", "white");

        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        cancelButton.getStyle().set("background-color", "#cccccc").set("color", "white");

        HorizontalLayout buttonsLayout = new HorizontalLayout(submitButton, cancelButton);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setPadding(true);

        VerticalLayout paymentFormLayout = new VerticalLayout();
        paymentFormLayout.setAlignItems(Alignment.CENTER);
        paymentFormLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        paymentFormLayout.getStyle().set("background-color", "#fff0f0").set("border-radius", "10px")
                .set("padding", "40px").set("box-shadow", "2px 2px 12px rgba(0, 0, 0, 0.1)");
        paymentFormLayout.add(
                new H1("Payment Details"),
                new HorizontalLayout(holderIDField, creditCardField),
                new HorizontalLayout(monthComboBox, yearComboBox, cvvField),
                buttonsLayout
        );

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.add(paymentFormLayout);

        // Add the main layout to the view
        add(mainLayout);
    }

    public void paymentResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        this.removeAll();
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }
}

