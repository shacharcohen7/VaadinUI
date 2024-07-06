package com.example.application.Presenter.StoreManagementPresenters.PoliciesActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.StoreManagementViews.PoliciesActionsViews.RemovePurchaseRuleView;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemovePurchaseRulePresenter {
    private RemovePurchaseRuleView view;
    private Map<String, Integer> descriptionToNum;
    private String userID;
    private String storeID;

    public RemovePurchaseRulePresenter(RemovePurchaseRuleView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
        descriptionToNum = new HashMap<>();
    }

    public List<String> getStoreCurrentPurchaseRules(){
        List<String> purchasePolicies = APIcalls.getStoreCurrentPurchaseRules(userID, storeID);
        for(int i=0 ; i<purchasePolicies.size() ; i++){
            descriptionToNum.put(purchasePolicies.get(i), i);
        }
        return purchasePolicies;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onRemoveButtonClicked(String rule) {
        String result = APIcalls.removePurchaseRuleFromStore(descriptionToNum.get(rule), userID, storeID);
        if (result.contains("success")) {
            view.removeSuccess("Purchase rule was removed");
        } else {
            view.removeFailure(result);
        }
    }
}
