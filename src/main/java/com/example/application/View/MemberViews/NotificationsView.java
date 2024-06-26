package com.example.application.View.MemberViews;

import com.example.application.Presenter.MemberPresenters.HistoryPresenter;
import com.example.application.Presenter.MemberPresenters.NotificationsPresenter;
import com.example.application.Util.AcquisitionDTO;
import com.example.application.Util.ReceiptDTO;
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
//import org.springframework.messaging.simp.annotation.SubscribeMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Route("NotificationsView")

public class NotificationsView extends VerticalLayout {
    private NotificationsPresenter presenter;
    private String userID;
    private Grid<AcquisitionDTO> acquisitionGrid;
    private Grid<ReceiptDTO> receiptGrid;
    private VerticalLayout receiptDetailsLayout;
    private Button backButton;

    public NotificationsView() {
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView() {
        presenter = new NotificationsPresenter(this, userID);
        createTopLayout();
        H1 header = new H1("Notifications");
        VerticalLayout layout = new VerticalLayout(header);
        layout.getStyle().set("background-color", "#ffc0cb"); // Set background color to dark pink
        layout.setSpacing(false);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        createNotificationsLayout();
        add(new Button("OK", event -> getUI().ifPresent(ui -> ui.navigate("MarketView"))));
    }

    public void createTopLayout(){
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.getStyle().set("background-color", "#fff0f0"); // Set background color
        Text helloMessage = new Text("Hello, " + presenter.getUserName());
        Button homeButton = new Button("Home", new Icon(VaadinIcon.HOME), event -> getUI().ifPresent(ui -> ui.navigate("MarketView")));
        Button shoppingCartButton = new Button("Shopping Cart", new Icon(VaadinIcon.CART),
                event -> getUI().ifPresent(ui -> ui.navigate("ShoppingCartView")));

        topLayout.add(helloMessage, homeButton, shoppingCartButton);
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

        add(topLayout);
    }

    public void createNotificationsLayout(){
        VerticalLayout notificationsLayout = new VerticalLayout();
        List<String> notifications = presenter.getNotifications();
        for(int i=0 ; i<notifications.size() ; i++){
            notificationsLayout.add(new HorizontalLayout(new Text(notifications.get(i))));
        }
        add(notificationsLayout);
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


//    @SubscribeMapping("/topic/notifications")
//    public void handleNotification(String notificationMessage) {
//        UI.getCurrent().access(() -> {
//            Notification.show(notificationMessage);
//        });
//    }

    public void logout(){
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }
}
