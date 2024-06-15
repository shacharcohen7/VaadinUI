package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.StoreActionsViews.AppointStoreManagerView;
import com.example.application.View.StoreActionsViews.UpdateManagerPermissionsView;

import java.util.LinkedList;
import java.util.List;

public class UpdateManagerPermissionsPresenter {
    private UpdateManagerPermissionsView view;
    private String userID;
    private String storeID;

    public UpdateManagerPermissionsPresenter(UpdateManagerPermissionsView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getStoreManagers(){
        return APIcalls.getStoreMangers(storeID);
    }

    public String getEmployeeUserName(String memberID){
        return APIcalls.getMemberName(memberID);
    }

    public boolean hasInventoryPermissions(String memberID){
        return APIcalls.hasInventoryPermission(memberID, storeID);
    }

    public boolean hasPurchasePermissions(String memberID){
        return APIcalls.hasPurchasePermission(memberID, storeID);
    }

    public void onUpdateButtonClicked(String usernameToUpdate, boolean inventoryPermissions, boolean purchasePermissions) {
        if (APIcalls.updateStoreManagerPermissions(userID, usernameToUpdate, storeID, inventoryPermissions, purchasePermissions).contains("success")) {
            view.updateSuccess("Permissions were updated successfully");
        } else {
            view.updateFailure("Appointment failed.");
        }
    }
}
