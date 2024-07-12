package com.example.application.Presenter.AdminPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
import com.example.application.View.AdminViews.RemovePaymentView;
import com.example.application.View.StoreManagementViews.InventoryActionsViews.RemoveProductFromStoreView;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;

public class RemovePaymentPresenter {
    private RemovePaymentView view;
    private String userID;
    private String storeID;

    public RemovePaymentPresenter(RemovePaymentView view, String userID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onRemoveButtonClicked(String licenceNum) {
        if (APIcalls.removeExternalPaymentService(licenceNum,
                VaadinSession.getCurrent().getAttribute("memberID").toString()).contains("success")) {
            view.removeSuccess("Payment service was removed");
        } else {
            view.removeFailure("Invalid input.");
        }
    }
}
