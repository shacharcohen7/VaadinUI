package com.example.application.View.MemberViews;

import com.example.application.Presenter.MemberPresenters.OpenStorePresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("OpenStoreView")
public class OpenStoreView extends VerticalLayout{
    private OpenStorePresenter presenter;
    private String userID;
    private TextField storeNameField;
    private TextField descriptionField;
    private Button doneButton;
    private Button cancelButton;

    public OpenStoreView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new OpenStorePresenter(this, userID);
        storeNameField = new TextField("store name");
        descriptionField = new TextField("store description");
        doneButton = new Button("Done", event -> {
            presenter.onDoneButtonClicked(storeNameField.getValue(), descriptionField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        add(
                new H1("Open new Store"),
                storeNameField,
                descriptionField,
                new HorizontalLayout(doneButton, cancelButton)
        );
    }

    public void openSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

    public void openFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
