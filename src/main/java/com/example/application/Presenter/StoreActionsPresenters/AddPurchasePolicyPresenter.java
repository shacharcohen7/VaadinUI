package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
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

    public List<String> getAllPurchaseRules(){
        Map<Integer, String> rulesMap = APIcalls.getAllPurchaseRules(userID, storeID);
        List<String> rulesList = new LinkedList<String>();
        for(Integer integer: rulesMap.keySet()){
            rulesList.add(integer + " " + rulesMap.get(integer));
        }
        return rulesList;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onAddButtonClicked(List<Integer> ruleNums, List<String> operators) {
        String result = APIcalls.addPurchaseRuleToStore(ruleNums, operators, userID, storeID);
        if (result.contains("success")) {
            view.addSuccess("Purchase policy was added");
        } else {
            view.addFailure(result);
        }
    }
}
