package com.example.application.Presenter.StoreManagementPresenters.PoliciesActionsPresenters.DiscountPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.DiscountValueDTO;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreManagementViews.PoliciesActionsViews.DiscountViews.AddSimpleDiscountView;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;

public class AddSimpleDiscountPresenter {
    private AddSimpleDiscountView view;
    private String userID;
    private String storeID;

    public AddSimpleDiscountPresenter(AddSimpleDiscountView view, String userID, String storeID){
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

    public void onAddButtonClicked(List<DiscountValueDTO> discs, List<String> numericOperators) {
        String result = APIcalls.addDiscountSimpleRuleToStore(discs, numericOperators, userID, storeID);
        if (result.contains("success")) {
            view.addSuccess("Discount rule was added");
        } else {
            view.addFailure(result);
        }
    }
}
