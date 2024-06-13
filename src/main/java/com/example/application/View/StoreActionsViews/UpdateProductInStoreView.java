package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AddProductToStorePresenter;
import com.example.application.Presenter.StoreActionsPresenters.UpdateProductInStorePresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("UpdateProductInStoreView")
public class UpdateProductInStoreView extends VerticalLayout implements HasUrlParameter<String> {
    private UpdateProductInStorePresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private ComboBox<String> productNameField;
    private IntegerField priceField;
    private IntegerField quantityField;
    private TextField descriptionField;
    private TextField categoryField;
    private Button updateButton;
    private Button cancelButton;

    public UpdateProductInStoreView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new UpdateProductInStorePresenter(this, userID, storeID);
        makeStoreQuery();
        productNameField = new ComboBox<String>("product");
        productNameField.setItems(presenter.getAllProductNames());
        priceField = new IntegerField("", "price");
        quantityField = new IntegerField("", "quantity");
        categoryField = new TextField("","category");
        descriptionField = new TextField("","description");
        updateButton = new Button("Update", event -> {
            presenter.onUpdateButtonClicked(productNameField.getValue(), priceField.getValue(),
                    quantityField.getValue(), descriptionField.getValue(), categoryField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
        });
        add(
                new H1("Update Product in Store"),
                productNameField,
                priceField,
                quantityField,
                descriptionField,
                categoryField,
                new HorizontalLayout(updateButton, cancelButton)
        );
    }

    public void updateSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
    }

    public void updateFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void makeStoreQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
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
