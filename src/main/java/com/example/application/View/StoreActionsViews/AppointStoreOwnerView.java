package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AppointStoreOwnerPresenter;
import com.example.application.Presenter.StoreActionsPresenters.RemoveProductFromStorePresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("AppointStoreOwnerView")
public class AppointStoreOwnerView extends VerticalLayout implements HasUrlParameter<String> {
    private AppointStoreOwnerPresenter presenter;
    private QueryParameters userStoreQuery;
    private String userID;
    private String storeID;
    private TextField usernameField;
    private Button appointButton;
    private Button cancelButton;

    public AppointStoreOwnerView(){}

    public void buildView(){
        presenter = new AppointStoreOwnerPresenter(this, userID, storeID);
        makeUserStoreQuery();
        usernameField = new TextField("","user name");
        appointButton = new Button("Appoint", event -> {
            presenter.onAppointButtonClicked(usernameField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
        });
        add(
                new H1("Appoint Store Owner"),
                usernameField,
                new HorizontalLayout(appointButton, cancelButton)
        );
    }

    public void appointmentSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
    }

    public void appointmentFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void makeUserStoreQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        parameters.put("storeID", List.of(storeID));
        userStoreQuery = new QueryParameters(parameters);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        storeID = parameters.getOrDefault("storeID", List.of("Unknown")).get(0);
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        buildView();
    }
}
