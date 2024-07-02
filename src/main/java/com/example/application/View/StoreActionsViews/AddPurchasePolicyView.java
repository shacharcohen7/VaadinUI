package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.AddPurchasePolicyPresenter;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Route("AddPurchasePolicyView")
public class AddPurchasePolicyView extends VerticalLayout implements HasUrlParameter<String> {
    private AddPurchasePolicyPresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private VerticalLayout rulesLayout;
    private List<Integer> ruleNums;
    private List<String> possibleRules;
    private List<String> operators;
    private Button addButton;
    private Button cancelButton;

    public AddPurchasePolicyView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        Object memberIdObj = VaadinSession.getCurrent().getAttribute("memberId");
        if (memberIdObj!=null){
            String memberId = memberIdObj.toString();
            WebSocketHandler.getInstance().addUI(memberId, UI.getCurrent());
        }
        presenter = new AddPurchasePolicyPresenter(this, userID, storeID);
        makeStoreQuery();
        createTopLayout();
        H1 header = new H1("Add Purchase Policy");
        VerticalLayout layout = new VerticalLayout(header);
        layout.getStyle().set("background-color", "#ffc0cb"); // Set background color to dark pink
        layout.setSpacing(false);
        layout.setAlignItems(Alignment.CENTER);
        add(layout);
        ruleNums = new LinkedList<Integer>();
        operators = new LinkedList<String>();
        possibleRules = presenter.getAllPurchaseRules();
        rulesLayout = new VerticalLayout();
        ComboBox<String> ruleField = new ComboBox<String>("rule");
        Button extendButton = new Button("extend", event -> extend());
        extendButton.setEnabled(false);
        extendButton.setDisableOnClick(true);
        ruleField.setItems(possibleRules);
        ruleField.addValueChangeListener(event -> {
            ruleNums.add(Integer.parseInt("" + event.getValue().charAt(0)));
            extendButton.setEnabled(true);
        });
        rulesLayout.add(ruleField, extendButton);
        addButton = new Button("Add", event -> {
            presenter.onAddButtonClicked(ruleNums, operators);
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("PurchasePolicyView", storeQuery));
        });
        add(
                rulesLayout,
                new HorizontalLayout(addButton, cancelButton)
        );
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

    public void extend(){
        ComboBox<String> operator = new ComboBox<String>("operator");
        operator.setItems("COND", "OR", "AND");
        operator.addValueChangeListener(event -> {
            operators.add(event.getValue());
        });

        ComboBox<String> ruleField = new ComboBox<String>("rule");
        Button extendButton = new Button("extend", event -> extend());
        extendButton.setEnabled(false);
        extendButton.setDisableOnClick(true);
        ruleField.setItems(possibleRules);
        ruleField.addValueChangeListener(event -> {
            ruleNums.add(Integer.parseInt("" + event.getValue().charAt(0)));
            extendButton.setEnabled(true);
        });
        rulesLayout.add(new HorizontalLayout(operator, ruleField), extendButton);
    }

    public void addSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("PurchasePolicyView", storeQuery));
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

    public void addFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
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
