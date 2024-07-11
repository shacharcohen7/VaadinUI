package com.example.application.Presenter.AdminPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.AdminViews.RemovePaymentView;
import com.example.application.View.AdminViews.RemoveSupplyView;
import com.vaadin.flow.server.VaadinSession;

public class RemoveSupplyPresenter {
    private RemoveSupplyView view;
    private String userID;
    private String storeID;

    public RemoveSupplyPresenter(RemoveSupplyView view, String userID){
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
        if (APIcalls.removeExternalSupplyService(licenceNum,
                VaadinSession.getCurrent().getAttribute("memberID").toString()).contains("success")) {
            view.removeSuccess("Supply service was removed");
        } else {
            view.removeFailure("Invalid input.");
        }
    }
}
