package com.example.application.View;

import com.example.application.Model.Product;
import com.example.application.Presenter.ShoppingCartPresenter;
import com.example.application.Presenter.StorePresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

@Route("ShoppingCartView")
public class ShoppingCartView extends VerticalLayout implements HasUrlParameter<String> {
    private ShoppingCartPresenter presenter;
    private QueryParameters userQuery;
    private String userID;
    private Text helloMessage;
    private Button homeButton;
    private Button shoppingCartButton;
    private Button loginButton;
    private Button logoutButton;
    private Button openStoreButton;
    private Button criticismButton;
    private Button ratingButton;
    private Button contactButton;
    private Button historyButton;
    private Button myProfileButton;
    private Button signInButton;
    private VerticalLayout cartProductsLayout;
    private VerticalLayout paymentLayout;
    private Map<String, Map<Product, Integer>> storeToProductsCart;

    public ShoppingCartView(){}

    public void buildView(){
        this.presenter = new ShoppingCartPresenter(this, userID);
        makeUserQuery();
        createTopLayout();
        add(new H1("Shopping Cart"));
        cartProductsLayout = new VerticalLayout();
        createCartProductsLayout();
        createSummaryLayout();
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
            openStoreButton = new Button("Open new Store", event -> {
                getUI().ifPresent(ui -> ui.navigate("OpenStoreView", userQuery));
            });
            criticismButton = new Button("Write Criticism", event -> {

            });
            ratingButton = new Button("Rate us", event -> {

            });
            contactButton = new Button("Contact us", event -> {

            });
            historyButton = new Button("History", event -> {

            });
            myProfileButton = new Button("My Profile", event -> {

            });
            topLayout.add(logoutButton, openStoreButton, criticismButton,
                    ratingButton, contactButton, historyButton, myProfileButton);
        }
        add(topLayout);
    }

    public void createCartProductsLayout() {
        storeToProductsCart = presenter.getStoreToProductsCart();
        for (String storeID : storeToProductsCart.keySet()) {
            cartProductsLayout.add(new H1(presenter.getStoreName(storeID)));
            Map<Product, Integer> storeProducts = storeToProductsCart.get(storeID);
            for (Product product : storeProducts.keySet()) {
                IntegerField quantityField = new IntegerField();
                quantityField.setLabel("quantity");
                quantityField.setMin(0);
                quantityField.setMax(10);
                quantityField.setValue(storeProducts.get(product));
                quantityField.setStepButtonsVisible(true);
                cartProductsLayout.add(
                        new HorizontalLayout(new Text("name: " + product.getProductName())),
                        new HorizontalLayout(new Text("description: " + product.getDescription())),
                        new HorizontalLayout(new Text("price: " + product.getPrice())),
                        quantityField,
                        new Button("Remove from Cart", event -> {presenter.removeProductCart();})
                );
            }
        }
        add(cartProductsLayout);
    }

    public void createSummaryLayout(){
        add(
                new H1("Total price: " + presenter.getTotalPrice())
        );
        paymentLayout = new VerticalLayout();
        paymentLayout.add(new Button("Continue to Payment", event -> {revealPaymentLayout();}));
        add(paymentLayout);
    }

    public void revealPaymentLayout(){
        paymentLayout.removeAll();
        IntegerField holderIDField = new IntegerField("holder ID");
        TextField creditCardField = new TextField("credit card");
        ComboBox<Integer> yearComboBox = new ComboBox<Integer>("year");
        yearComboBox.setItems(2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032);
        ComboBox<Integer> monthComboBox = new ComboBox<Integer>("month");
        monthComboBox.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        IntegerField cvvField = new IntegerField("cvv");

        paymentLayout.add(
                new HorizontalLayout(holderIDField, creditCardField),
                new HorizontalLayout(monthComboBox, yearComboBox, cvvField),
                new HorizontalLayout(
                        new Button("Submit", event -> {
                            presenter.onSubmitButtonClicked(presenter.getTotalPrice(), creditCardField.getValue(),
                                    cvvField.getValue(), monthComboBox.getValue(), yearComboBox.getValue(),
                                    holderIDField.getValue());
                        }),
                        new Button("Cancel", event -> {
                            this.removeAll();
                            buildView();
                        })
                )
        );
    }

    public void paymentFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

    public void paymentSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
    }

    public void removeProductCartResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        this.removeAll();
        buildView();
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
        userID = parameters.getOrDefault("userID", List.of("Unknown")).get(0);
        buildView();
    }
}
