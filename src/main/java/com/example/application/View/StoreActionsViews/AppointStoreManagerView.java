package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AppointStoreManagerPresenter;
import com.example.application.Presenter.StoreActionsPresenters.AppointStoreOwnerPresenter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("AppointStoreManagerView")
public class AppointStoreManagerView extends VerticalLayout implements HasUrlParameter<String> {
    private AppointStoreManagerPresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private TextField usernameField;
    private ComboBox<Boolean> inventoryPermissions;
    private ComboBox<Boolean> purchasePermissions;
    private Button appointButton;
    private Button cancelButton;

    public AppointStoreManagerView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new AppointStoreManagerPresenter(this, userID, storeID);
        makeStoreQuery();
        usernameField = new TextField("","user name");
        inventoryPermissions = new ComboBox<Boolean>("Inventory Permissions");
        inventoryPermissions.setItems(true, false);
        purchasePermissions = new ComboBox<Boolean>("Purchase Permissions");
        purchasePermissions.setItems(true, false);
        appointButton = new Button("Appoint", event -> {
            presenter.onAppointButtonClicked(usernameField.getValue(), inventoryPermissions.getValue(),
                    purchasePermissions.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
        });
        add(
                new H1("Appoint Store Owner"),
                usernameField,
                inventoryPermissions,
                purchasePermissions,
                new HorizontalLayout(appointButton, cancelButton)
        );
    }

    public void appointmentSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
    }

    public void appointmentFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void makeStoreQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        parameters.put("storeID", List.of(storeID));
        storeQuery = new QueryParameters(parameters);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        storeID = parameters.getOrDefault("storeID", List.of("Unknown")).get(0);
        buildView();
    }
}
