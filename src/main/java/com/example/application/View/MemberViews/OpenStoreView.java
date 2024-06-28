package com.example.application.View.MemberViews;

import com.example.application.Presenter.MemberPresenters.OpenStorePresenter;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("OpenStoreView")
public class OpenStoreView extends VerticalLayout{
    private OpenStorePresenter presenter;
    private String userID;
    private TextField storeNameField;
    private TextField descriptionField;
    private Button doneButton;
    private Button cancelButton;

    public OpenStoreView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new OpenStorePresenter(this, userID);
        createTopLayout();
        H1 header = new H1("Open New Store");
        VerticalLayout layout = new VerticalLayout(header);
        layout.getStyle().set("background-color", "#ffc0cb"); // Set background color to dark pink
        layout.setSpacing(false);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        storeNameField = new TextField("store name");
        descriptionField = new TextField("store description");
        doneButton = new Button("Done", event -> {
            presenter.onDoneButtonClicked(storeNameField.getValue(), descriptionField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
        });
        add(
                storeNameField,
                descriptionField,
                new HorizontalLayout(doneButton, cancelButton)
        );
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

    public void openSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

    public void openFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}
