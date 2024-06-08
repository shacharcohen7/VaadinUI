package com.example.application.View;

import com.example.application.Presenter.StorePresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("StoreView")
public class StoreView extends VerticalLayout implements HasUrlParameter<String> {
    private StorePresenter presenter;
    private String userID;
    private H1 welcomeToStore;
    private Text helloMessage;
    private Button shoppingCartButton;
    private Button loginButton;
    private Button signInButton;
    private TextField productNameField;
    private TextField categoryField;
    private TextField keywordsField;
    private IntegerField minPriceField;
    private IntegerField maxPriceField;
    private Button searchButton;
    private VerticalLayout productsFoundLayout;
    private VerticalLayout allProductsLayout;
    private HashMap<String, ProductDTO> allProducts;

    public StoreView() {
        presenter = new StorePresenter(this, userID);
        welcomeToStore = new H1("");
        createTopLayout();
        add(welcomeToStore);
        createSearchLayout();
        createAllProductsLayout();
    }

    public void createTopLayout(){
        helloMessage = new Text("Hello, " + presenter.getUserName());
        shoppingCartButton = new Button("Shopping Cart", event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView"));
        });
        loginButton = new Button("Log In", event -> {
            getUI().ifPresent(ui -> ui.navigate("LoginView"));
        });
        signInButton = new Button("Sign In", event -> {
            getUI().ifPresent(ui -> ui.navigate("SignInView"));
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
        searchButton = new Button("search", event -> {
            presenter.onSearchButtonClicked(productNameField.getValue(), categoryField.getValue(),
                    keywordsField.getValue(), minPriceField.getValue(),
                    maxPriceField.getValue());
        });
        productsFoundLayout = new VerticalLayout();

        add(
                new HorizontalLayout(
                        productNameField,
                        categoryField,
                        keywordsField,
                        minPriceField,
                        maxPriceField,
                        searchButton
                ),
                productsFoundLayout
        );
    }

    public void createAllProductsLayout(){
        add(new H1("All store products:"));
        allProducts = presenter.getAllProducts();
        allProductsLayout = new VerticalLayout();
        for (ProductDTO productDto : allProducts.values()) {
            allProductsLayout.add(
                    new HorizontalLayout(new Text("name: " + productDto.getName())),
                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
                    new HorizontalLayout(
                            new IntegerField("","quantity"),
                            new Button("Add to Cart")
                    )
            );
        }
        add(allProductsLayout);
    }

    public void showInStoreSearchResult(HashMap<String, ProductDTO> productsFound){
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
        String storeName = parameters.getOrDefault("storeName", List.of("Unknown")).get(0);
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        welcomeToStore.setText("Welcome to " + storeName);
    }
}
