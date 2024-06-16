package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.PurchasePolicyPresenter;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("PurchasePolicyView")
public class PurchasePolicyView extends VerticalLayout implements HasUrlParameter<String> {
    private PurchasePolicyPresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private Button BackButton;
    private Button AddPolicyButton;

    public PurchasePolicyView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new PurchasePolicyPresenter(this, userID, storeID);
        makeStoreQuery();
        AddPolicyButton = new Button("Add New Policy", event -> {
            getUI().ifPresent(ui -> ui.navigate("AddPurchasePolicyView", storeQuery));
        });
        BackButton = new Button("Back To Store", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
        });
        createPurchaseLayout();
        add(new HorizontalLayout(AddPolicyButton, BackButton));
    }

    public void createPurchaseLayout(){
        add(new H1("Store Purchase Policies:"));
        List<String> purchaseRules = presenter.getStoreCurrentPurchaseRules();
        for(int i=0 ; i<purchaseRules.size() ; i++){
            add(new HorizontalLayout(new Text(purchaseRules.get(i))),
                    new Button("Remove", event -> {}));
        }
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
