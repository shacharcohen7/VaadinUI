package com.example.application.View;

import com.example.application.Presenter.FinalShoppingCartPresenter;
import com.example.application.Presenter.ShoppingCartPresenter;
import com.example.application.Util.CartDTO;
import com.example.application.Util.ProductDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@Route("FinalShoppingCartView")
public class FinalShoppingCartView extends VerticalLayout{
    private FinalShoppingCartPresenter presenter;
    private String userID;
    private VerticalLayout cartLayout;
    private VerticalLayout paymentLayout;
    private AtomicInteger remainingTime = new AtomicInteger(60);
    private TextField timerField;
    private Button confirmAndPayButton;
    private Button cancelButton;
    private CartDTO cartDTO;


    public FinalShoppingCartView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        cartDTO = (CartDTO) VaadinSession.getCurrent().getAttribute("cartDTO");
        buildView();
        Notification.show("You have 60 seconds to complete your payment.",3000, Notification.Position.MIDDLE);
    }

    public void buildView(){
        this.presenter = new FinalShoppingCartPresenter(this, userID, cartDTO);
        H1 header = new H1("Final Shopping Cart");

        // Timer field under the title
        timerField = new TextField("Time Remaining");
        timerField.setReadOnly(true);
        timerField.setWidth("150px");

//        VerticalLayout layout = new VerticalLayout(header, timerField);
        VerticalLayout layout = new VerticalLayout(header);
        layout.getStyle().set("background-color", "#ffc0cb"); // Set background color to dark pink
        layout.setSpacing(false);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);

        cartLayout = new VerticalLayout();
        createCartProductsLayout();
        createSummaryLayout();
    }

    public void createCartProductsLayout() {
        Map<String, Map<String, List<Integer>>> storeToProductsCart = presenter.getFinalCart().getStoreToProducts();
        for (String storeID : storeToProductsCart.keySet()) {
            VerticalLayout basketLayout = new VerticalLayout();
            // Set background color and border style using CSS
            basketLayout.getStyle()
                    .set("background-color", "#fff0f0") // Light pink background color
                    .set("border", "2px solid #e91e63") // Pink border with 1px width
                    .set("border-radius", "10px") // Rounded corners
                    .set("padding", "20px") // Padding inside the basket
                    .set("margin", "20px") // Margin between baskets
                    .set("box-shadow", "2px 2px 12px rgba(0, 0, 0, 0.1)"); // Subtle shadow for depth

            basketLayout.setAlignItems(Alignment.CENTER);
            basketLayout.setJustifyContentMode(JustifyContentMode.CENTER);

            H1 storeNameHeader = new H1(presenter.getStoreName(storeID));
            storeNameHeader.getStyle()
                    .set("color", "#e91e63") // Pink color for store name
                    .set("margin-bottom", "20px"); // Margin below the store name
            basketLayout.add(storeNameHeader);

            Map<String, List<Integer>> basket = storeToProductsCart.get(storeID);
            HorizontalLayout productRow = new HorizontalLayout();
            productRow.setWidthFull();
            productRow.setSpacing(true);
            basketLayout.add(productRow);

            int productsPerRow = 3;
            int currentColumn = 0;
            HorizontalLayout currentRow = new HorizontalLayout();
            currentRow.setSpacing(true);

            for (String productName : basket.keySet()) {
                if (currentColumn >= productsPerRow) {
                    productRow.add(currentRow);
                    currentRow = new HorizontalLayout();
                    currentRow.setSpacing(true);
                    currentColumn = 0;
                }

                ProductDTO product = presenter.getProduct(productName, storeID);
                VerticalLayout productLayout = new VerticalLayout();
                productLayout.getStyle()
                        .set("border", "1px solid #ccc") // Light gray border around each product block
                        .set("padding", "10px") // Padding inside the product block
                        .set("border-radius", "10px") // Rounded corners
                        .set("box-shadow", "1px 1px 6px rgba(0, 0, 0, 0.1)"); // Subtle shadow for depth

                Span productNameSpan = new Span("Name: " + productName);
                productNameSpan.getStyle()
                        .set("font-weight", "bold") // Bold font for product name
                        .set("color", "#333"); // Darker color for readability

                Span productDescriptionSpan = new Span("Description: " + product.getDescription());
                productDescriptionSpan.getStyle()
                        .set("color", "#666"); // Medium color for description

                Span productPriceSpan = new Span("Price: " + basket.get(productName).get(1));
                productPriceSpan.getStyle()
                        .set("color", "#e91e63") // Pink color for price
                        .set("font-weight", "bold"); // Bold font for price

                IntegerField quantityField = new IntegerField();
                quantityField.setLabel("Quantity");
                quantityField.setMin(0);
                quantityField.setMax(Math.min(10, product.getQuantity()));
                quantityField.setValue(basket.get(productName).get(0));
                quantityField.setStepButtonsVisible(true);

                productLayout.add(productNameSpan, productDescriptionSpan, productPriceSpan, quantityField);
                currentRow.add(productLayout);
                currentColumn++;
            }
            // Add the last row if it contains any products
            if (currentColumn > 0) {
                productRow.add(currentRow);
            }
            cartLayout.add(basketLayout);
        }
        add(cartLayout);
    }

    public void createSummaryLayout() {
        VerticalLayout summaryLayout = new VerticalLayout();
        summaryLayout.getStyle().set("background-color", "#fff0f0"); // Set background color

        double totalPrice = presenter.getTotalPrice(); // Assuming presenter has a method to get the total price
        summaryLayout.add(new H1("Total price: " + totalPrice));

        confirmAndPayButton = new Button("Confirm and Pay", event -> {
            getUI().ifPresent(ui -> ui.navigate("PaymentView"));
        });        cancelButton = new Button("Cancel", event -> cancelCart());

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmAndPayButton, cancelButton);
        summaryLayout.add(buttonLayout);
        add(summaryLayout);

        startCountdown();
    }

    private void startCountdown() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime.get() > 0) {
                    remainingTime.decrementAndGet();
                    getUI().ifPresent(ui -> ui.access(() -> timerField.setValue(formatTime(remainingTime.get()))));
                } else {
                    getUI().ifPresent(ui -> ui.access(() -> {
                        timerField.setValue("Time expired!");
                        timer.cancel();
                        confirmAndPayButton.setEnabled(false);
                        cancelCart();
                    }));
                }
            }
        }, 0, 1000);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

//    private void performPayment() {
//        try {
//            presenter.onSubmitButtonClicked(); // Assuming the presenter has a method to handle the purchase
//            Notification.show("Payment Successful", 3000, Notification.Position.MIDDLE);
//            getUI().ifPresent(ui -> ui.navigate("MarketView"));
//        } catch (Exception e) {
//            Notification.show("Payment Failed: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
//        }
//    }

    private void cancelCart() {
        Notification.show("Payment Cancelled", 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("ShoppingCartView"));
    }


    public void revealPaymentLayout(){
        paymentLayout.removeAll();
        TextField holderIDField = new TextField("holder ID");
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
                            presenter.onSubmitButtonClicked(creditCardField.getValue(),
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

    public void paymentResult(String message){
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        this.removeAll();
        buildView();
    }


}
