package com.example.application.View.GuestViews;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("GuestStoreView")
public class GuestStoreView extends VerticalLayout {
    VerticalLayout storeProducts = new VerticalLayout(
            new HorizontalLayout(new Text("name: skirt")),
            new HorizontalLayout(new Text("description: blue")),
            new HorizontalLayout(new Text("price: 43")),
            new HorizontalLayout(
                    new TextField("", "quantity"),
                    new Button("Add to Cart")
            )
    );

    public GuestStoreView() {
        add(
                new HorizontalLayout(
                        new Text("Hello, Guest"),
                        new Button("Shopping Cart"),
                        new Button("Log In", event -> {
                            getUI().ifPresent(ui -> ui.navigate("LoginView"));
                        }),
                        new Button("Sign In", event -> {
                            getUI().ifPresent(ui -> ui.navigate("SignInView"));
                        })
                ),
                new H1("Welcome to ZARA"),
                new Text("Search for product:"),
                new HorizontalLayout(
                        new TextField("", "product name"),
                        new TextField("", "category"),
                        new TextField("", "keywords"),
                        new TextField("", "minimum price"),
                        new TextField("", "maximum price"),
                        new Button("search")
                ),
                new H1("Store products:"),
                storeProducts
        );
    }

    public void addProductToBasket(){

    }
}
