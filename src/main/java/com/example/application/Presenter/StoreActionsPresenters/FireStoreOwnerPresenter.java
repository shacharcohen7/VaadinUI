package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreActionsViews.FireStoreOwnerView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;

public class FireStoreOwnerPresenter {
    private FireStoreOwnerView view;
    private String userID;
    private String storeID;

    public FireStoreOwnerPresenter(FireStoreOwnerView view, String userID, String storeID){
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

    public List<String> getStoreOwners(){
        return APIcalls.getStoreOwners(storeID);
    }

    public void onFireButtonClicked(String productName) {
        if (APIcalls.fireStoreOwner(userID, storeID, productName).contains("success")) {
            view.fireSuccess("Store owner was fired successfully");
        } else {
            view.fireFailure("Invalid input.");
        }
    }
}
