package com.example.application.Presenter.StoreActionsPresenters;

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

    public void onUpdateButtonClicked(String usernameToUpdate, boolean inventoryPermissions, boolean purchasePermissions) {
        //call updateManagerPermissions()
        boolean success = true;
        if (success) {
            view.updateSuccess("Permissions were updated successfully");
        } else {
            view.updateFailure("Appointment failed.");
        }
    }
}
