package com.example.application.Presenter.AdminPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.PaymentServiceDTO;
import com.example.application.Util.SupplyServiceDTO;
import com.example.application.View.AdminViews.AddPaymentView;
import com.example.application.View.AdminViews.AddSupplyView;
import com.vaadin.flow.server.VaadinSession;

import java.util.HashSet;
import java.util.List;

public class AddSupplyPresenter {
    private AddSupplyView view;
    private String userID;
    private String storeID;

    public AddSupplyPresenter(AddSupplyView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public String getUserName(){
        return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
    }

    public List<String> getCategories(){
        return APIcalls.getCategories();
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onAddButtonClicked(String licensedDealerNumber, String supplyServiceName,
                                   String country, String city) {
        HashSet<String> countries = new HashSet<>();
        countries.add(country);
        HashSet<String> cities = new HashSet<>();
        countries.add(city);
        String result = APIcalls.addExternalSupplyService(VaadinSession.getCurrent().getAttribute("memberID").toString(),
                new SupplyServiceDTO(licensedDealerNumber, supplyServiceName, countries, cities));
        if (result.contains("success")) {
            view.addSuccess("Supply service was added");
        } else {
            view.addFailure(result);
        }
    }
}
