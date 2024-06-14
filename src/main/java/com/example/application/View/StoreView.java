package com.example.application.View;

import com.example.application.Presenter.StorePresenter;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
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

@Route("StoreView")
public class StoreView extends VerticalLayout implements HasUrlParameter<String> {
    private StorePresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private VerticalLayout productsFoundLayout;

    public StoreView() {}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new StorePresenter(this, userID, storeID);
        makeStoreQuery();
        createTopLayout();
        add(new VerticalLayout(new H1("Welcome to " + presenter.getStoreName())));
        createSearchLayout();
        createAllProductsLayout();
        if(presenter.isMember()){
            if(presenter.isStoreOwner()){
                createInventoryLayout();
                createPurchaseLayout();
                createHRLayout();
                createOtherActionsLayout();
            }
            else if(presenter.isStoreManager()){
                if(presenter.hasInventoryPermissions()){
                    createInventoryLayout();
                }
                if(presenter.hasPurchasePermissions()){
                    createPurchaseLayout();
                }
            }
        }
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
        Button searchButton = new Button("search", event -> {
            presenter.onSearchButtonClicked(productNameField.getValue(), categoryField.getValue(),
                    keywordsField.getValue(), minPriceField.getValue(),
                    maxPriceField.getValue());
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
                        maxPriceField
                ),
                new HorizontalLayout(searchButton, clearButton),
                productsFoundLayout
        );
        add(searchLayout);
    }

    public void createAllProductsLayout(){
        VerticalLayout allProductsLayout = new VerticalLayout();
        allProductsLayout.add(new H1("All store products:"));
        List<ProductDTO> allProducts = presenter.getAllProducts();
        for (int i=0 ; i<allProducts.size(); i++) {
            ProductDTO product = allProducts.get(i);
            IntegerField quantityField = new IntegerField();
            quantityField.setLabel("quantity");
            quantityField.setMin(0);
            quantityField.setMax(10);
            quantityField.setValue(1);
            quantityField.setStepButtonsVisible(true);
            allProductsLayout.add(
                    new HorizontalLayout(new Text("name: " + product.getName())),
                    new HorizontalLayout(new Text("description: " + product.getDescription())),
                    new HorizontalLayout(new Text("price: " + product.getPrice())),
                    quantityField,
                    new Button("Add to Cart", event -> {
                        presenter.onAddToCartButtonClicked(product, quantityField.getValue());
                    })
            );
        }
        add(allProductsLayout);
    }

    public void createInventoryLayout(){
        VerticalLayout inventoryLayout = new VerticalLayout();
        inventoryLayout.add(new H1("Inventory Actions:"),
                new HorizontalLayout(
                    new Button("Add Product to Store", event -> {
                        getUI().ifPresent(ui -> ui.navigate("AddProductToStoreView", storeQuery));
                    }),
                    new Button("Remove Product from Store", event -> {
                        getUI().ifPresent(ui -> ui.navigate("RemoveProductFromStoreView", storeQuery));
                    }),
                    new Button("Update Product in Store", event -> {
                        getUI().ifPresent(ui -> ui.navigate("UpdateProductInStoreView", storeQuery));
                })
        ));
        add(inventoryLayout);
    }

    public void createPurchaseLayout(){
        VerticalLayout purchaseLayout = new VerticalLayout();
        purchaseLayout.add(new H1("Policies Actions:"),
                new HorizontalLayout(
                new Button("Update Discount Policy")
        ));
        add(purchaseLayout);
    }

    public void createHRLayout(){
        VerticalLayout HRLayout = new VerticalLayout();
        HRLayout.add(new H1("HR Actions:"),
                new HorizontalLayout(
                        new Button("Get all Employees", event -> {
                            getUI().ifPresent(ui -> ui.navigate("GetAllEmployeesView", storeQuery));
                        }),
                        new Button("Appoint Store Owner", event -> {
                            getUI().ifPresent(ui -> ui.navigate("AppointStoreOwnerView", storeQuery));
                        }),
                        new Button("Fire Store Owner")
                ),
                new HorizontalLayout(
                        new Button("Appoint Store Manager", event -> {
                            getUI().ifPresent(ui -> ui.navigate("AppointStoreManagerView", storeQuery));
                        }),
                        new Button("Update store manager permissions", event -> {
                            getUI().ifPresent(ui -> ui.navigate("UpdateManagerPermissionsView", storeQuery));
                        }),
                        new Button("Fire Store Manager")
                )
        );
        add(HRLayout);
    }

    public void createOtherActionsLayout(){
        VerticalLayout otherActionsLayout = new VerticalLayout();
        otherActionsLayout.add(new H1("Other Actions:"));
        HorizontalLayout actions = new HorizontalLayout();
        if(presenter.isOpened()){
            actions.add(new Button("Close Store", event -> {
                closeStoreConfirm();
            }));
        }
        else{
            actions.add(new Button("Reopen Store"));
        }
        actions.add(
                new Button("Contact Clients"),
                new Button("Get Store History")
        );
        otherActionsLayout.add(actions);
        add(otherActionsLayout);
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

    public void closeStoreConfirm(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Close Store");
        dialog.setText("Are you sure you want to close this store?");
        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());
        dialog.setConfirmText("Yes");
        dialog.addConfirmListener(event -> {
            if(presenter.onCloseButtonClicked()){
                this.removeAll();
                buildView();
            }
        });
        dialog.open();
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
