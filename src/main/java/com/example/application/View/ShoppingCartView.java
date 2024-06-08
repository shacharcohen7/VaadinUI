package com.example.application.View;

import com.example.application.Presenter.ShoppingCartPresenter;
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
    private Button signInButton;
    private VerticalLayout cartProductsLayout;
    private Map<String, Map<ProductDTO, Integer>> storeToProductsCart;

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
            topLayout.add(logoutButton);
        }
        add(topLayout);
    }

    public void createCartProductsLayout() {
        storeToProductsCart = presenter.getStoreToProductsCart();
        for (String storeID : storeToProductsCart.keySet()) {
            cartProductsLayout.add(new H1(presenter.getStoreName(storeID)));
            Map<ProductDTO, Integer> storeProducts = storeToProductsCart.get(storeID);
            for (ProductDTO productDTO : storeProducts.keySet()) {
                IntegerField quantityField = new IntegerField();
                quantityField.setLabel("quantity");
                quantityField.setMin(0);
                quantityField.setMax(10);
                quantityField.setValue(storeProducts.get(productDTO));
                quantityField.setStepButtonsVisible(true);
                cartProductsLayout.add(
                        new HorizontalLayout(new Text("name: " + productDTO.getName())),
                        new HorizontalLayout(new Text("description: " + productDTO.getDescription())),
                        new HorizontalLayout(new Text("price: " + productDTO.getPrice())),
                        quantityField,
                        new Button("Remove from Cart", event -> {presenter.removeProductCart();})
                );
            }
        }
        add(cartProductsLayout);
    }

    public void createSummaryLayout(){
        add(
                new H1("Summery:"),
                new Text("Total price: " + presenter.getTotalPrice()),
                new Button("Pay")
        );
    }

    public void removeProductCartResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        cartProductsLayout.removeAll();
        createCartProductsLayout();
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
