package com.example.application.View;

import com.example.application.Model.Product;
import com.example.application.Presenter.ShoppingCartPresenter;
import com.example.application.Presenter.StorePresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("ShoppingCartView")
public class ShoppingCartView extends VerticalLayout {
    private ShoppingCartPresenter presenter;
    private String userID;
    private VerticalLayout cartLayout;
    private VerticalLayout paymentLayout;

    public ShoppingCartView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        this.presenter = new ShoppingCartPresenter(this, userID);
        createTopLayout();
        add(new VerticalLayout(new H1("Shopping Cart")));
        cartLayout = new VerticalLayout();
        createCartProductsLayout();
        createSummaryLayout();
    }

    public void createTopLayout(){
        HorizontalLayout topLayout = new HorizontalLayout();
        Text helloMessage = new Text("Hello, " + presenter.getUserName());
        Button homeButton = new Button("Home", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        Button shoppingCartButton = new Button("Shopping Cart", event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView"));
        });
        topLayout.add(helloMessage, homeButton, shoppingCartButton);
        if(!presenter.isMember()){
            Button loginButton = new Button("Log In", event -> {
                getUI().ifPresent(ui -> ui.navigate("LoginView"));
            });
            Button signInButton = new Button("Sign In", event -> {
                getUI().ifPresent(ui -> ui.navigate("SignInView"));
            });
            topLayout.add(loginButton, signInButton);
        }
        else{
            Button openStoreButton = new Button("Open new Store", event -> {
                getUI().ifPresent(ui -> ui.navigate("OpenStoreView"));
            });
            Button criticismButton = new Button("Write Criticism", event -> {

            });
            Button ratingButton = new Button("Rate us", event -> {

            });
            Button contactButton = new Button("Contact us", event -> {

            });
            Button historyButton = new Button("History", event -> {

            });
            Button myProfileButton = new Button("My Profile", event -> {
                getUI().ifPresent(ui -> ui.navigate("MyProfileView"));
            });
            Button logoutButton = new Button("Log Out", event -> {
                logoutConfirm();
            });

            topLayout.add(openStoreButton, criticismButton,
                    ratingButton, contactButton, historyButton, myProfileButton, logoutButton);
        }
        add(topLayout);
    }

    public void createCartProductsLayout() {
        Map<String, Map<Product, Integer>> storeToProductsCart = presenter.getStoreToProductsCart();
        for (String storeID : storeToProductsCart.keySet()) {
            VerticalLayout basketLayout = new VerticalLayout();
            basketLayout.add(new H1(presenter.getStoreName(storeID)));
            Map<Product, Integer> storeProducts = storeToProductsCart.get(storeID);
            for (Product product : storeProducts.keySet()) {
                IntegerField quantityField = new IntegerField();
                quantityField.setLabel("quantity");
                quantityField.setMin(0);
                quantityField.setMax(10);
                quantityField.setValue(storeProducts.get(product));
                quantityField.setStepButtonsVisible(true);
                basketLayout.add(
                        new HorizontalLayout(new Text("name: " + product.getProductName())),
                        new HorizontalLayout(new Text("description: " + product.getDescription())),
                        new HorizontalLayout(new Text("price: " + product.getPrice())),
                        quantityField,
                        new Button("Remove from Cart", event -> {presenter.removeProductCart();})
                );
            }
            cartLayout.add(basketLayout);
        }
        add(cartLayout);
    }

    public void createSummaryLayout(){
        VerticalLayout summaryLayout = new VerticalLayout();
        summaryLayout.add(new H1("Total price: " + presenter.getTotalPrice()));
        paymentLayout = new VerticalLayout();
        paymentLayout.add(new Button("Continue to Payment", event -> {revealPaymentLayout();}));
        summaryLayout.add(paymentLayout);
        add(summaryLayout);
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
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

    public void removeProductCartResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        this.removeAll();
        buildView();
    }

    public void logoutConfirm(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Logout");
        dialog.setText("Are you sure you want to log out?");
        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());
        dialog.setConfirmText("Yes");
        dialog.addConfirmListener(event -> presenter.logOut());
        dialog.open();
    }

    public void logout(){
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

}
