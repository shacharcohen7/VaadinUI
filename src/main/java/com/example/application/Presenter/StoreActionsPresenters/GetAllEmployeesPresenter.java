package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.UserDTO;
import com.example.application.View.StoreActionsViews.GetAllEmployeesView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;

public class GetAllEmployeesPresenter {
    private GetAllEmployeesView view;
    private String userID;
    private String storeID;

    public GetAllEmployeesPresenter(GetAllEmployeesView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public String getEmployeeUserName(String memberID){
        return APIcalls.getMemberName(memberID);
    }

    public List<String> getStoreOwners(){
        return APIcalls.getStoreOwners(storeID);
    }

    public List<String> getStoreManagers(){
        return APIcalls.getStoreMangers(storeID);
    }

    public boolean hasInventoryPermissions(String memberID){
        return APIcalls.hasInventoryPermission(memberID, storeID);
    }

    public boolean hasPurchasePermissions(String memberID){
        return APIcalls.hasPurchasePermission(memberID, storeID);
    }
}
