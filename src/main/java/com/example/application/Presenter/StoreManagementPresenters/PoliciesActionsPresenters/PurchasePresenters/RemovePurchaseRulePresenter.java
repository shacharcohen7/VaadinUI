package com.example.application.Presenter.StoreManagementPresenters.PoliciesActionsPresenters.PurchasePresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.StoreManagementViews.PoliciesActionsViews.PurchaseViews.RemovePurchaseRuleView;
import com.example.application.WebSocketUtil.WebSocketHandler;
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
        if (purchasePolicies != null) {
            for(int i=0 ; i<purchasePolicies.size() ; i++){
                descriptionToNum.put(purchasePolicies.get(i), i);
            }
            return purchasePolicies;
        }
        return null;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            Object memberIdObj = VaadinSession.getCurrent().getAttribute("memberID");
            if (memberIdObj!= null) {
                WebSocketHandler.getInstance().closeConnection(memberIdObj.toString());
            }
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
