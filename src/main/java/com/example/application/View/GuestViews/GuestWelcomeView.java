package com.example.application.View.GuestViews;

import com.example.application.Model.MarketModel;
import com.example.application.Model.ProductModel;
import com.example.application.Presenter.GuestPresenters.WelcomeGuestPresenter;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.HashMap;

@Route("")
public class GuestWelcomeView extends VerticalLayout {
    private WelcomeGuestPresenter presenter;
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
    private HorizontalLayout allStores;

    public GuestWelcomeView() {
        presenter = new WelcomeGuestPresenter(this, new MarketModel());

        shoppingCartButton = new Button("Shopping Cart", event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView"));
        });
        loginButton = new Button("Log In", event -> {
            getUI().ifPresent(ui -> ui.navigate("LoginView"));
        });
        signInButton = new Button("Sign In", event -> {
            getUI().ifPresent(ui -> ui.navigate("SignInView"));
        });

        add(new HorizontalLayout(new Text("Hello, Guest"), shoppingCartButton, loginButton, signInButton));

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

        add(
                new H1("Welcome to The Market Place!"),
                new Text("Search for product:"),
                new HorizontalLayout(
                        productNameField,
                        categoryField,
                        keywordsField,
                        minPriceField,
                        maxPriceField,
                        minStoreRatingField,
                        searchButton
                )
        );

        allStores = new HorizontalLayout(new Button("ZARA", event -> {
                            getUI().ifPresent(ui -> ui.navigate("StoreView"));
                        }));

        add(
                new H1("All Stores:"),
                allStores
        );
    }

    public void showGeneralSearchResult(HashMap<String, ProductModel> productsFound){
        getUI().ifPresent(ui -> ui.navigate("SearchResultView"));
    }
}