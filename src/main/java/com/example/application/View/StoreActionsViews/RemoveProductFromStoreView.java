package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AddProductToStorePresenter;
import com.example.application.Presenter.StoreActionsPresenters.RemoveProductFromStorePresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("RemoveProductFromStoreView")
public class RemoveProductFromStoreView extends VerticalLayout implements HasUrlParameter<String> {
    private RemoveProductFromStorePresenter presenter;
    private QueryParameters userStoreQuery;
    private String userID;
    private String storeID;
    private ComboBox<String> productNameField;
    private Button removeButton;
    private Button cancelButton;

    public RemoveProductFromStoreView(){}

    public void buildView(){
        presenter = new RemoveProductFromStorePresenter(this, userID, storeID);
        makeUserStoreQuery();
        productNameField = new ComboBox<String>("product");
        productNameField.setItems(presenter.getAllProductNames());
        removeButton = new Button("Remove", event -> {
            removeConfirm();
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
        });
        add(
                new H1("Remove Product from Store"),
                productNameField,
                new HorizontalLayout(removeButton, cancelButton)
        );
    }

    public void removeConfirm(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Remove Product");
        dialog.setText("Are you sure you want to remove this product from store?");
        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());
        dialog.setConfirmText("Yes");
        dialog.addConfirmListener(event -> presenter.onRemoveButtonClicked(productNameField.getValue()));
        dialog.open();
    }

    public void removeSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
    }

    public void removeFailure(String message) {
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
