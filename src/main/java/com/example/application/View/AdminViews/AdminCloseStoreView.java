package com.example.application.View.AdminViews;

import com.example.application.Presenter.AdminPresenters.AdminCloseStorePresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("AdminCloseStoreView")
public class AdminCloseStoreView  extends VerticalLayout implements HasUrlParameter<String> {
    private AdminCloseStorePresenter presenter;
    private QueryParameters userQuery;
    private String userID;
    private ComboBox<String> storeIDField;
    private Button closeButton;
    private Button cancelButton;

    public AdminCloseStoreView(){}

    public void buildView(){
        presenter = new AdminCloseStorePresenter(this, userID);
        makeUserQuery();
        storeIDField = new ComboBox<String>("store ID");
        storeIDField.setItems(presenter.getAllStoreIDs());
        closeButton = new Button("Close", event -> {
            closeStoreConfirm();
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
        });
        add(
                new H1("Close Store"),
                storeIDField,
                new HorizontalLayout(closeButton, cancelButton)
        );
    }

    public void closeStoreConfirm(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Close Store");
        dialog.setText("Are you sure you want to close this store?");
        dialog.setCancelable(true);
        dialog.addCancelListener(event -> dialog.close());
        dialog.setConfirmText("Yes");
        dialog.addConfirmListener(event -> {
            presenter.onCloseButtonClicked(storeIDField.getValue());
        });
        dialog.open();
    }

    public void closeSuccess(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
    }

    public void closeFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
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
