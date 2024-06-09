package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.MemberPresenters.OpenStorePresenter;
import com.example.application.Presenter.StoreActionsPresenters.AddProductToStorePresenter;
import com.vaadin.flow.component.button.Button;
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

@Route("AddProductToStoreView")
public class AddProductToStoreView extends VerticalLayout implements HasUrlParameter<String>{
    private AddProductToStorePresenter presenter;
    private QueryParameters userStoreQuery;
    private String userID;
    private String storeID;
    private TextField productNameField;
    private IntegerField priceField;
    private IntegerField quantityField;
    private TextField descriptionField;
    private TextField categoryField;
    private Button addButton;
    private Button cancelButton;

    public AddProductToStoreView(){}

    public void buildView(){
        presenter = new AddProductToStorePresenter(this, userID, storeID);
        makeUserStoreQuery();
        productNameField = new TextField("","product name");
        priceField = new IntegerField("", "price");
        quantityField = new IntegerField("", "quantity");
        categoryField = new TextField("","category");
        descriptionField = new TextField("","description");
        addButton = new Button("Add", event -> {
            presenter.onAddButtonClicked(productNameField.getValue(), priceField.getValue(),
                    quantityField.getValue(), descriptionField.getValue(), categoryField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
        });
        add(
                new H1("Add Product to Store"),
                productNameField,
                priceField,
                quantityField,
                descriptionField,
                categoryField,
                new HorizontalLayout(addButton, cancelButton)
        );
    }

    public void addSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
    }

    public void addFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void makeUserStoreQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        parameters.put("storeID", List.of(userID));
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
