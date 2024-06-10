package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.View.StoreActionsViews.AppointStoreOwnerView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;

public class AppointStoreOwnerPresenter {
    private AppointStoreOwnerView view;
    private String userID;
    private String storeID;

    public AppointStoreOwnerPresenter(AppointStoreOwnerView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public void onAppointButtonClicked(String usernameToAppoint) {
        //call appointStoreOwner()
        boolean success = true;
        if (success) {
            view.appointmentSuccess("Appointment performed successfully");
        } else {
            view.appointmentFailure("Appointment failed.");
        }
    }
}
