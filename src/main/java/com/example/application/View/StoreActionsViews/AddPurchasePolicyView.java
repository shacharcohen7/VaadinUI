package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AddProductToStorePresenter;
import com.example.application.Presenter.StoreActionsPresenters.AddPurchasePolicyPresenter;
import com.example.application.Util.ProductDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Route("AddPurchasePolicyView")
public class AddPurchasePolicyView extends VerticalLayout implements HasUrlParameter<String> {
    private AddPurchasePolicyPresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private VerticalLayout rulesLayout;
    private List<Integer> ruleNums;
    private List<String> possibleRules;
    private List<String> operators;
    private Button addButton;
    private Button cancelButton;

    public AddPurchasePolicyView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new AddPurchasePolicyPresenter(this, userID, storeID);
        makeStoreQuery();
        ruleNums = new LinkedList<Integer>();
        operators = new LinkedList<String>();
        possibleRules = presenter.getAllPurchaseRules();
        rulesLayout = new VerticalLayout();
        ComboBox<String> ruleField = new ComboBox<String>("rule");
        Button extendButton = new Button("extend", event -> extend());
        extendButton.setEnabled(false);
        extendButton.setDisableOnClick(true);
        ruleField.setItems(possibleRules);
        ruleField.addValueChangeListener(event -> {
            ruleNums.add(Integer.parseInt("" + event.getValue().charAt(0)));
            extendButton.setEnabled(true);
        });
        rulesLayout.add(ruleField, extendButton);
        addButton = new Button("Add", event -> {
            presenter.onAddButtonClicked(ruleNums, operators);
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("PurchasePolicyView", storeQuery));
        });
        add(
                new H1("Add Purchase Policy"),
                rulesLayout,
                new HorizontalLayout(addButton, cancelButton)
        );
    }

    public void extend(){
        ComboBox<String> operator = new ComboBox<String>("operator");
        operator.setItems("COND", "OR", "AND");
        operator.addValueChangeListener(event -> {
            operators.add(event.getValue());
        });

        ComboBox<String> ruleField = new ComboBox<String>("rule");
        Button extendButton = new Button("extend", event -> extend());
        extendButton.setEnabled(false);
        extendButton.setDisableOnClick(true);
        ruleField.setItems(possibleRules);
        ruleField.addValueChangeListener(event -> {
            ruleNums.add(Integer.parseInt("" + event.getValue().charAt(0)));
            extendButton.setEnabled(true);
        });
        rulesLayout.add(new HorizontalLayout(operator, ruleField), extendButton);
    }

    public void addSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("PurchasePolicyView", storeQuery));
    }

    public void addFailure(String message) {
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
