package com.example.application.View.MemberViews;

import com.example.application.Presenter.MemberPresenter.OpenStorePresenter;
import com.example.application.Presenter.SignInPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("OpenStoreView")
public class OpenStoreView extends VerticalLayout implements HasUrlParameter<String> {
    private OpenStorePresenter presenter;
    private QueryParameters userQuery;
    private String userID;
    private TextField storeNameField;
    private TextField descriptionField;
    private Button doneButton;
    private Button cancelButton;

    public OpenStoreView(){}

    public void buildView(){
        presenter = new OpenStorePresenter(this, userID);
        makeUserQuery();
        storeNameField = new TextField("store name");
        descriptionField = new TextField("store description");
        doneButton = new Button("Done", event -> {
            presenter.onDoneButtonClicked(storeNameField.getValue(), descriptionField.getValue());
        });
        cancelButton = new Button("Cancel", event -> {
            getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
        });
        add(
                new H1("Open new Store"),
                storeNameField,
                descriptionField,
                new HorizontalLayout(doneButton, cancelButton)
        );
    }

    public void open(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
        getUI().ifPresent(ui -> ui.navigate("MarketView", userQuery));
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
