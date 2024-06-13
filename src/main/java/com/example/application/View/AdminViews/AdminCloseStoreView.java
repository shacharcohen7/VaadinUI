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
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("AdminCloseStoreView")
public class AdminCloseStoreView  extends VerticalLayout {
    private AdminCloseStorePresenter presenter;
    private String userID;
    private ComboBox<String> storeIDField;
    private Button closeButton;
    private Button cancelButton;

    public AdminCloseStoreView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        buildView();
    }

    public void buildView(){
        presenter = new AdminCloseStorePresenter(this, userID);
        storeIDField = new ComboBox<String>("store ID");
        storeIDField.setItems(presenter.getAllStoreIDs());
        closeButton = new Button("Close", event -> {
            closeStoreConfirm();
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView"));
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
        getUI().ifPresent(ui -> ui.navigate("MarketView"));
    }

    public void closeFailure(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }

}
