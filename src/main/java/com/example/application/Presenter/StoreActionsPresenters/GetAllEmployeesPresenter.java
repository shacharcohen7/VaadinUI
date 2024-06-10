package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.View.StoreActionsViews.GetAllEmployeesView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;

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

    public List<String> getStoreOwners(){
        //call getInfoAboutRoles and retrieve all store owner usernames
        return new LinkedList<String>();
    }

    public List<String> getStoreManagers(){
        //call getInfoAboutRoles and retrieve all store manager usernames
        return new LinkedList<String>();
    }

    public boolean hasInventoryPermissions(String username){
        //call getManagerAuthorisations and retrieve inventoryPermissions
        return false;
    }

    public boolean hasPurchasePermissions(String username){
        //call getManagerAuthorisations and retrieve purchasePermissions
        return false;
    }
}
