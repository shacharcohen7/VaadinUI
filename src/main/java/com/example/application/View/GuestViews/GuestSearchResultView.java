package com.example.application.View.GuestViews;

import com.example.application.Model.ProductModel;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

import java.util.HashMap;

@Route("SearchResultView")
public class GuestSearchResultView extends VerticalLayout {
    public GuestSearchResultView(HashMap<String, ProductModel> productsFound){
        VerticalLayout products = new VerticalLayout();
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
                new H1("Products found:"),
                products
        );
        for (ProductModel productModel : productsFound.values()) {
            add(
                    new HorizontalLayout(new Text("name: " + productModel.getProductName())),
                    new HorizontalLayout(new Text("description: " + productModel.getDescription())),
                    new HorizontalLayout(new Text("price: " + productModel.getPrice())),
                    new HorizontalLayout(
                            new IntegerField("quantity"),
                            new Button("Add to Cart")
                    )
            );
        }
    }
}
