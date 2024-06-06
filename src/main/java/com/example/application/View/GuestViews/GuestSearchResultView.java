package com.example.application.View.GuestViews;

import com.example.application.Model.ProductModel;
import com.example.application.Presenter.GuestPresenters.GuestSearchResultPresenter;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.ProductSearchService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.LinkedList;

@Route("GuestSearchResultView")
public class GuestSearchResultView extends VerticalLayout {
    private GuestSearchResultPresenter presenter;
    private VerticalLayout products;

    public GuestSearchResultView(){
        presenter = new GuestSearchResultPresenter(this);
        products = new VerticalLayout();
        for (ProductDTO productDto : ProductSearchService.getProductsFound().values()) {
            products.add(
                    new HorizontalLayout(new Text("name: " + productDto.getName())),
                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
                    new HorizontalLayout(
                            new IntegerField("", "quantity"),
                            new Button("Add to Cart")
                    )
            );
        }
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
    }

//    public void showProducts(HashMap<String, ProductDTO> productsFound){
//        for (ProductDTO productDto : productsFound.values()) {
//            products.add(
//                    new HorizontalLayout(new Text("name: " + productDto.getName())),
//                    new HorizontalLayout(new Text("description: " + productDto.getDescription())),
//                    new HorizontalLayout(new Text("price: " + productDto.getPrice())),
//                    new HorizontalLayout(
//                            new IntegerField("quantity"),
//                            new Button("Add to Cart")
//                    )
//            );
//        }
//    }
}
