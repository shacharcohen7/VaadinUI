package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AppointStoreManagerPresenter;
import com.example.application.Presenter.StoreActionsPresenters.UpdateManagerPermissionsPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("UpdateManagerPermissionsView")
public class UpdateManagerPermissionsView extends VerticalLayout implements HasUrlParameter<String> {
    private UpdateManagerPermissionsPresenter presenter;
    private QueryParameters userStoreQuery;
    private String userID;
    private String storeID;
    private ComboBox<String> storeManagerField;
    private ComboBox<Boolean> inventoryPermissions;
    private ComboBox<Boolean> purchasePermissions;
    private Button updateButton;
    private Button cancelButton;

    public UpdateManagerPermissionsView(){}

    public void buildView(){
        presenter = new UpdateManagerPermissionsPresenter(this, userID, storeID);
        makeUserStoreQuery();
        storeManagerField = new ComboBox<String>("Store Manager");
        storeManagerField.setItems(presenter.getStoreManagers());
        inventoryPermissions = new ComboBox<Boolean>("Inventory Permissions");
        inventoryPermissions.setItems(true, false);
        purchasePermissions = new ComboBox<Boolean>("Purchase Permissions");
        purchasePermissions.setItems(true, false);
        updateButton = new Button("Update", event -> {
            presenter.onUpdateButtonClicked(storeManagerField.getValue(), inventoryPermissions.getValue(),
                    purchasePermissions.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
        });
        add(
                new H1("Update Store Manager Permissions"),
                storeManagerField,
                inventoryPermissions,
                purchasePermissions,
                new HorizontalLayout(updateButton, cancelButton)
        );
    }

    public void updateSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
    }

    public void updateFailure(String message) {
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
