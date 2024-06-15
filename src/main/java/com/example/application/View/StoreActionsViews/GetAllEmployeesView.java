package com.example.application.View.StoreActionsViews;

import com.example.application.Presenter.StoreActionsPresenters.GetAllEmployeesPresenter;
import com.example.application.Presenter.StoreActionsPresenters.RemoveProductFromStorePresenter;
import com.example.application.Util.UserDTO;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("GetAllEmployeesView")
public class GetAllEmployeesView extends VerticalLayout implements HasUrlParameter<String> {
    private GetAllEmployeesPresenter presenter;
    private QueryParameters storeQuery;
    private String userID;
    private String storeID;
    private Button OKButton;

    public GetAllEmployeesView(){}

    public void buildView(){
        userID = VaadinSession.getCurrent().getAttribute("userID").toString();
        presenter = new GetAllEmployeesPresenter(this, userID, storeID);
        makeStoreQuery();
        OKButton = new Button("OK", event -> {
            getUI().ifPresent(ui -> ui.navigate("StoreView", storeQuery));
        });
        createOwnersLayout();
        createManagersLayout();
        add(OKButton);
    }

    public void createOwnersLayout(){
        VerticalLayout ownersLayout = new VerticalLayout();
        ownersLayout.add(new H1("Store Owners:"));
        List<String> storeOwners = presenter.getStoreOwners();
        for(int i=0 ; i<storeOwners.size() ; i++){
            VerticalLayout ownerLayout = new VerticalLayout();
            ownerLayout.add(new HorizontalLayout(new Text("Store Owner - " + presenter.getEmployeeUserName(storeOwners.get(i)))));
            ownersLayout.add(ownerLayout);
        }
        add(ownersLayout);
    }

    public void createManagersLayout(){
        VerticalLayout managersLayout = new VerticalLayout();
        managersLayout.add(new H1("Store Managers:"));
        List<String> storeManagers = presenter.getStoreManagers();
        for(int i=0 ; i<storeManagers.size() ; i++){
            VerticalLayout managerLayout = new VerticalLayout();
            managerLayout.add(
                    new HorizontalLayout(new Text("Store Manager - " + presenter.getEmployeeUserName(storeManagers.get(i)))),
                    new HorizontalLayout(new Text("Inventory permissions: " + presenter.hasInventoryPermissions(storeManagers.get(i)))),
                    new HorizontalLayout(new Text("Purchase permissions: " + presenter.hasPurchasePermissions(storeManagers.get(i))))
            );
            managersLayout.add(managerLayout);
        }
        add(managersLayout);
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
