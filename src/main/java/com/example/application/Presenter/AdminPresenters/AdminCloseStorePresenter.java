package com.example.application.Presenter.AdminPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.StoreDTO;
import com.example.application.View.AdminViews.AdminCloseStoreView;

import java.util.LinkedList;
import java.util.List;

public class AdminCloseStorePresenter {
    private AdminCloseStoreView view;
    private String userID;
    private String storeID;

    public AdminCloseStorePresenter(AdminCloseStoreView view, String userID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getAllStoreIDs(){
        List<String> storeIDs = new LinkedList<String>();
        List<StoreDTO> storesDTO = APIcalls.getAllStores();
        for (int i=0 ; i<storesDTO.size() ; i++){
            storeIDs.add(storesDTO.get(i).getStore_ID());
        }
        return storeIDs;
    }

    public void onCloseButtonClicked(String storeID) {
        if (APIcalls.closeStore(userID, storeID).contains("success")) {
            view.closeSuccess("Store was closed");
        } else {
            view.closeFailure("Invalid input.");
        }
    }
}
