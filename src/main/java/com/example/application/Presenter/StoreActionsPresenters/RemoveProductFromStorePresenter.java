package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.RemoveProductFromStoreView;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RemoveProductFromStorePresenter {
    private RemoveProductFromStoreView view;
    private String userID;
    private String storeID;

    public RemoveProductFromStorePresenter(RemoveProductFromStoreView view, String userID, String storeID){
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

    public List<String> getAllProductNames(){
        List<ProductDTO> products = APIcalls.getStoreProducts(storeID);
        List<String> productNames = new LinkedList<String>();
        for(int i=0 ;i<products.size() ; i++){
            productNames.add(products.get(i).getName());
        }
        return productNames;
    }

    public void onRemoveButtonClicked(String productName) {
        if (APIcalls.removeProductFromStore(userID, storeID, productName).contains("success")) {
            view.removeSuccess("Product was removed");
        } else {
            view.removeFailure("Invalid input.");
        }
    }
}
