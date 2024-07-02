package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.TestRuleDTO;
import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.AddPurchasePolicyView;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddPurchasePolicyPresenter {
    private AddPurchasePolicyView view;
    private String userID;
    private String storeID;

    public AddPurchasePolicyPresenter(AddPurchasePolicyView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getCategories(){
        return APIcalls.getCategories();
    }

    public List<String> getAllProductNames(){
        List<ProductDTO> products = APIcalls.getStoreProducts(storeID);
        List<String> productNames = new LinkedList<String>();
        for(int i=0 ;i<products.size() ; i++){
            productNames.add(products.get(i).getName());
        }
        return productNames;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onAddButtonClicked(List<TestRuleDTO> Rules, List<String> logicOperators) {
        String result = APIcalls.addPurchaseRuleToStore(Rules, logicOperators, userID, storeID);
        if (result.contains("success")) {
            view.addSuccess("Purchase policy was added");
        } else {
            view.addFailure(result);
        }
    }
}
