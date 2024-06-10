package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.View.StoreActionsViews.AppointStoreManagerView;
import com.example.application.View.StoreActionsViews.AppointStoreOwnerView;

public class AppointStoreManagerPresenter {
    private AppointStoreManagerView view;
    private String userID;
    private String storeID;

    public AppointStoreManagerPresenter(AppointStoreManagerView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public void onAppointButtonClicked(String usernameToAppoint, boolean inventoryPermissions, boolean purchasePermissions) {
        //call appointStoreOwner()
        boolean success = true;
        if (success) {
            view.appointmentSuccess("Appointment performed successfully");
        } else {
            view.appointmentFailure("Appointment failed.");
        }
    }
}
