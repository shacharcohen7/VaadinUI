package com.example.application.View;

import com.example.application.Presenter.StorePresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
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

@Route("StoreView")
public class StoreView extends VerticalLayout implements HasUrlParameter<String> {
    private StorePresenter presenter;
    private QueryParameters userQuery;
    private String userID;
    private String storeID;
    private Text helloMessage;
    private Button shoppingCartButton;
    private Button homeButton;
    private Button loginButton;
    private Button logoutButton;
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

    public StoreView() {}

    public void buildView(){
        presenter = new StorePresenter(this, userID, storeID);
        makeUserQuery();
        createTopLayout();
        add(new H1("Welcome to " + presenter.getStoreName()));
        createSearchLayout();
        createAllProductsLayout();
    }

    public void createTopLayout(){
        HorizontalLayout topLayout = new HorizontalLayout();
        helloMessage = new Text("Hello, " + presenter.getUserName());
        homeButton = new Button("Home", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
        });
        shoppingCartButton = new Button("Shopping Cart", event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView", userQuery));
        });
        topLayout.add(helloMessage, homeButton, shoppingCartButton);
        if(!presenter.isMember()){
            loginButton = new Button("Log In", event -> {
                getUI().ifPresent(ui -> ui.navigate("LoginView", userQuery));
            });
            signInButton = new Button("Sign In", event -> {
                getUI().ifPresent(ui -> ui.navigate("SignInView", userQuery));
            });
            topLayout.add(loginButton, signInButton);
        }
        else{
            logoutButton = new Button("Log Out", event -> {
                presenter.logOut();
            });
            topLayout.add(logoutButton);
        }
        add(topLayout);
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
            IntegerField quantityField = new IntegerField();
            quantityField.setLabel("quantity");
            quantityField.setMin(0);
            quantityField.setMax(10);
            quantityField.setValue(1);
            quantityField.setStepButtonsVisible(true);
            allProductsLayout.add(
                    new HorizontalLayout(new Text("name: " + productDto.getName())),
                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
                    quantityField,
                    new Button("Add to Cart", event -> {
                        presenter.onAddToCartButtonClicked(productDto, quantityField.getValue());
                    })
            );
        }
        add(allProductsLayout);
    }

    public void showInStoreSearchResult(HashMap<String, ProductDTO> productsFound){
        productsFoundLayout.removeAll();
        productsFoundLayout.add(new H1("Search results:"));
        for (ProductDTO productDto : productsFound.values()) {
            IntegerField quantityField = new IntegerField();
            quantityField.setLabel("quantity");
            quantityField.setMin(0);
            quantityField.setMax(10);
            quantityField.setValue(1);
            quantityField.setStepButtonsVisible(true);
            productsFoundLayout.add(
                    new HorizontalLayout(new Text("name: " + productDto.getName())),
                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
                    quantityField,
                    new Button("Add to Cart", event -> {
                        presenter.onAddToCartButtonClicked(productDto, quantityField.getValue());
                    })
            );
        }
    }

    public void addProductCartResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void logout(){
        getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
    }

    public void makeUserQuery(){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        userQuery = new QueryParameters(parameters);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        Map<String, List<String>> parameters = beforeEvent.getLocation().getQueryParameters().getParameters();
        storeID = parameters.getOrDefault("storeID", List.of("Unknown")).get(0);
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        buildView();
    }
}
