package com.example.application.View;

import com.example.application.Presenter.MarketPresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("MarketView")
public class MarketView extends VerticalLayout implements HasUrlParameter<String>{
    private MarketPresenter presenter;
    private String userID;
    private Text helloMessage;
    private Button shoppingCartButton;
    private Button loginButton;
    private Button signInButton;
    private TextField productNameField;
    private TextField categoryField;
    private TextField keywordsField;
    private IntegerField minPriceField;
    private IntegerField maxPriceField;
    private IntegerField minStoreRatingField;
    private Button searchButton;
    private VerticalLayout productsFoundLayout;
    private HorizontalLayout allStoresLayout;
    private List<String> storeNames;

    public MarketView() {

    }

    public void createTopLayout(){
        helloMessage = new Text("Hello, " + presenter.getUserName());
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        QueryParameters queryParameters = new QueryParameters(parameters);
        shoppingCartButton = new Button("Shopping Cart", event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView", queryParameters));
        });
        loginButton = new Button("Log In", event -> {
            getUI().ifPresent(ui -> ui.navigate("LoginView", queryParameters));
        });
        signInButton = new Button("Sign In", event -> {
            getUI().ifPresent(ui -> ui.navigate("SignInView", queryParameters));
        });
        add(new HorizontalLayout(helloMessage, shoppingCartButton, loginButton, signInButton));
    }

    public void createSearchLayout(){
        add(new Text("Search for product:"));
        productNameField = new TextField("", "product name");
        categoryField = new TextField("", "category");
        keywordsField = new TextField("", "keywords");
        minPriceField = new IntegerField("", "minimum price");
        maxPriceField = new IntegerField("", "maximum price");
        minStoreRatingField = new IntegerField("", "minimum store rating");
        searchButton = new Button("search", event -> {
            presenter.onSearchButtonClicked(productNameField.getValue(), categoryField.getValue(),
                    keywordsField.getValue(), minPriceField.getValue(),
                    maxPriceField.getValue(), minStoreRatingField.getValue());
        });
        productsFoundLayout = new VerticalLayout();

        add(
                new HorizontalLayout(
                    productNameField,
                    categoryField,
                    keywordsField,
                    minPriceField,
                    maxPriceField,
                    minStoreRatingField,
                    searchButton
                ),
                productsFoundLayout
        );
    }

    public void createAllStoresLayout(){
        add(new H1("All Stores:"));
        storeNames = presenter.getAllStoreNames();
        allStoresLayout = new HorizontalLayout();
        for(int i=0 ; i<storeNames.size(); i++){
            allStoresLayout.add(new Button(storeNames.get(i), event -> {
                UI.getCurrent().access(() -> presenter.onStoreButtonClicked(event.getSource().getText()));}));
        }
        add(allStoresLayout);
    }

    public void openStore(String storeName){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("storeName", List.of(storeName));
        parameters.put("userName", List.of(presenter.getUserName()));
        QueryParameters queryParameters = new QueryParameters(parameters);
        getUI().ifPresent(ui -> ui.navigate("StoreView", queryParameters));
    }

    public void showGeneralSearchResult(HashMap<String, ProductDTO> productsFound){
        productsFoundLayout.removeAll();
        for (ProductDTO productDto : productsFound.values()) {
            productsFoundLayout.add(
                    new HorizontalLayout(new Text("name: " + productDto.getName())),
                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
                    new HorizontalLayout(
                            new IntegerField("","quantity"),
                            new Button("Add to Cart")
                    )
            );
        }
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        presenter = new MarketPresenter(this, userID);
        createTopLayout();
        add(new H1("Welcome to The Market Place!"));
        createSearchLayout();
        createAllStoresLayout();
    }
}