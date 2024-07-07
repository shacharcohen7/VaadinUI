package com.example.application.View.MemberViews;

import com.example.application.Presenter.MemberPresenters.HistoryPresenter;
import com.example.application.Util.AcquisitionDTO;
import com.example.application.Util.ReceiptDTO;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.Map;

@Route("HistoryView")
public class HistoryView extends VerticalLayout {
    private HistoryPresenter presenter;
    private String userID;
    private Grid<AcquisitionDTO> acquisitionGrid;
    private Grid<ReceiptDTO> receiptGrid;
    private VerticalLayout receiptDetailsLayout;
    private Button backButton;

    public HistoryView() {
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        Object memberIdObj = VaadinSession.getCurrent().getAttribute("memberId");
        if (memberIdObj!=null){
            String memberId = memberIdObj.toString();
            WebSocketHandler.getInstance().addUI(memberId, UI.getCurrent());
        }
        buildView();
    }

    public void buildView() {
        presenter = new HistoryPresenter(this, userID);
        createTopLayout();
        H1 header = new H1("Acquisition History");
        VerticalLayout layout = new VerticalLayout(header);
        layout.getStyle().set("background-color", "#ffc0cb"); // Set background color to dark pink
        layout.setSpacing(false);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);

        acquisitionGrid = new Grid<>(AcquisitionDTO.class);
        acquisitionGrid.setColumns("date", "acquisitionId", "totalPrice");
        acquisitionGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                presenter.onAcquisitionSelected(event.getValue().getAcquisitionId());
            }
        });

        receiptGrid = new Grid<>(ReceiptDTO.class);
        receiptGrid.setColumns("receiptId", "storeId", "userId");
        receiptGrid.setVisible(false);

        receiptDetailsLayout = new VerticalLayout();
        receiptDetailsLayout.setVisible(false);

        backButton = new Button("Back", event -> {
            receiptGrid.setVisible(false);
            receiptDetailsLayout.setVisible(false);
            acquisitionGrid.setVisible(true);
            backButton.setVisible(false);
        });
        backButton.setVisible(false);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.add(acquisitionGrid, receiptGrid, receiptDetailsLayout, backButton);

        add(mainLayout);
        presenter.loadAcquisitionHistory();
    }

    public void createTopLayout() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.getStyle().set("background-color", "#fff0f0"); // Set background color

        Text helloMessage = new Text("Hello, " + presenter.getUserName());
        Button homeButton = new Button("Home", new Icon(VaadinIcon.HOME), event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        Button shoppingCartButton = new Button("Shopping Cart", new Icon(VaadinIcon.CART), event -> {
            getUI().ifPresent(ui -> ui.navigate("ShoppingCartView"));
        });
        topLayout.add(helloMessage, homeButton, shoppingCartButton);

        if (!presenter.isMember()) {
            Button loginButton = new Button("Log In", event -> {
                getUI().ifPresent(ui -> ui.navigate("LoginView"));
            });
            Button signInButton = new Button("Sign In", event -> {
                getUI().ifPresent(ui -> ui.navigate("SignInView"));
            });
            topLayout.add(loginButton, signInButton);
        } else {
            Button openStoreButton = new Button("Open new Store", event -> {
                getUI().ifPresent(ui -> ui.navigate("OpenStoreView"));
            });
            Button historyButton = new Button("History", event -> {
                getUI().ifPresent(ui -> ui.navigate("HistoryView"));
            });
            Button myProfileButton = new Button("My Profile", event -> {
                getUI().ifPresent(ui -> ui.navigate("MyProfileView"));
            });
            Button notificationsButton = new Button("Notifications", event -> {
                getUI().ifPresent(ui -> ui.navigate("NotificationsView"));
            });
            Button logoutButton = new Button("Log Out", event -> {
                logoutConfirm();
            });
            topLayout.add(openStoreButton, historyButton, myProfileButton, notificationsButton, logoutButton);
        }

        add(topLayout);
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

    public void showAcquisitions(List<AcquisitionDTO> acquisitions) {
        acquisitionGrid.setItems(acquisitions);
    }

    public void showReceipts(List<ReceiptDTO> receipts) {
        receiptGrid.setItems(receipts);
    }

    public void showReceipts(Map<String, ReceiptDTO> receipts) {
        receiptGrid.setItems(receipts.values());
        receiptGrid.setVisible(true);
        acquisitionGrid.setVisible(false);
        backButton.setVisible(true);
        receiptDetailsLayout.removeAll();

        receiptGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                ReceiptDTO receipt = event.getValue();
                receiptDetailsLayout.removeAll();
                receiptDetailsLayout.add(new H1("Receipt Details"),
                        new HorizontalLayout(new H1("Receipt ID: " + receipt.getReceiptId())),
                        new HorizontalLayout(new H1("Store ID: " + receipt.getStoreId())),
                        new HorizontalLayout(new H1("User ID: " + receipt.getUserId())),
                        new H1("Products"));

                receipt.getProductList().forEach((product, details) -> {
                    receiptDetailsLayout.add(new HorizontalLayout(new H1("Product: " + product),
                            new H1("Price: " + details.get(1)),
                            new H1("Quantity: " + details.get(0))));
                });

                receiptDetailsLayout.setVisible(true);
            }
        });
    }

    public void showError(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
