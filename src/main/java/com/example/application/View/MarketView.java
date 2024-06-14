package com.example.application.View;

import com.example.application.Model.Product;
import com.example.application.Presenter.MarketPresenter;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.StoreDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
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

@Route("MarketView")
public class MarketView extends VerticalLayout {
    private MarketPresenter presenter;
    private String userID;
    private VerticalLayout productsFoundLayout;

    public MarketView() {
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new MarketPresenter(this, userID);
        createTopLayout();
        add(new VerticalLayout(new H1("Welcome to The Market Place!")));
        createSearchLayout();
        createAllStoresLayout();
        if(presenter.isAdmin()){
            createAdminLayout();
        }
    }

    public void createTopLayout(){
        HorizontalLayout topLayout = new HorizontalLayout();
        Text helloMessage = new Text("Hello, " + presenter.getUserName());
        Button homeButton = new Button("Home");
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

    public void createSearchLayout(){
        VerticalLayout searchLayout = new VerticalLayout();
        searchLayout.add(new Text("Search for product:"));
        TextField productNameField = new TextField("product name");
        TextField categoryField = new TextField("category");
        MultiSelectComboBox<String> keywordsField = new MultiSelectComboBox<String>("keywords");
        keywordsField.setItems("clothes", "shoes", "food", "optic", "electricity", "toys", "health", "sport",
                "women", "men", "children", "beauty", "travel", "gifts", "office", "coffee", "home");
        IntegerField minPriceField = new IntegerField("minimum price");
        IntegerField maxPriceField = new IntegerField("maximum price");
        IntegerField minStoreRatingField = new IntegerField("minimum store rating");
        Button searchButton = new Button("search", event -> {
            presenter.onSearchButtonClicked(productNameField.getValue(), categoryField.getValue(),
                    keywordsField.getValue(), minPriceField.getValue(),
                    maxPriceField.getValue(), minStoreRatingField.getValue());
        });
        Button clearButton = new Button("clear", event -> {
            this.removeAll();
            this.buildView();
        });
        productsFoundLayout = new VerticalLayout();

        searchLayout.add(
                new HorizontalLayout(
                        productNameField,
                        categoryField,
                        keywordsField,
                        minPriceField,
                        maxPriceField,
                        minStoreRatingField
                ),
                new HorizontalLayout(searchButton, clearButton),
                productsFoundLayout
        );
        add(searchLayout);
    }

    public void createAllStoresLayout(){
        VerticalLayout storesLayout = new VerticalLayout();
        storesLayout.add(new H1("All Stores:"));
        List<StoreDTO> allStoresDtos = presenter.getAllStores();
        HorizontalLayout storeNamesLayout = new HorizontalLayout();
        for(int i=0 ; i<allStoresDtos.size(); i++){
            StoreDTO storeDto = allStoresDtos.get(i);
            storeNamesLayout.add(new Button(storeDto.getStoreName(), event -> {
                UI.getCurrent().access(() -> goToStore(storeDto.getStore_ID()));}));
        }
        storesLayout.add(storeNamesLayout);
        add(storesLayout);
    }

    public void createAdminLayout(){
        VerticalLayout adminLayout = new VerticalLayout();
        adminLayout.add(new H1("Administration:"));
        Button closeStoreButton = new Button("Close Store", event -> {
            getUI().ifPresent(ui -> ui.navigate("AdminCloseStoreView"));
        });
        Button deleteMemberButton = new Button("Delete Member", event -> {

        });
        Button contactClientsButton = new Button("Contact Clients", event -> {

        });
        Button getHistoryButton = new Button("Market History", event -> {

        });
        Button marketProgressButton = new Button("Watch Market Progress", event -> {

        });
        adminLayout.add(new HorizontalLayout(closeStoreButton, deleteMemberButton, contactClientsButton, getHistoryButton, marketProgressButton));
        add(adminLayout);
    }

    public void goToStore(String storeID){
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("storeID", List.of(storeID));
        parameters.put("userID", List.of(userID));
        QueryParameters userStoreQuery = new QueryParameters(parameters);
        getUI().ifPresent(ui -> ui.navigate("StoreView", userStoreQuery));
    }

    public void showGeneralSearchResult(List<ProductDTO> productsFound){
        productsFoundLayout.removeAll();
        productsFoundLayout.add(new H1("Search results:"));
        for (int i=0 ; i < productsFound.size() ; i++) {
            ProductDTO product = productsFound.get(i);
            IntegerField quantityField = new IntegerField();
            quantityField.setLabel("quantity");
            quantityField.setMin(0);
            quantityField.setMax(10);
            quantityField.setValue(1);
            quantityField.setStepButtonsVisible(true);
            productsFoundLayout.add(
                    new HorizontalLayout(new Text("name: " + product.getName())),
                    new HorizontalLayout(new Text("description: " + product.getDescription())),
                    new HorizontalLayout(new Text("price: " + product.getPrice())),
                    quantityField,
                    new Button("Add to Cart", event -> {
                        presenter.onAddToCartButtonClicked(product, quantityField.getValue());
                    })
            );
        }
    }

    public void addProductCartResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
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
        this.removeAll();
        buildView();
    }
}