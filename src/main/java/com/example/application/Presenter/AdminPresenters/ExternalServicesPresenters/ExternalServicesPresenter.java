package com.example.application.Presenter.AdminPresenters.ExternalServicesPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.AdminViews.ExternalServicesViews.ExternalServicesView;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

public class ExternalServicesPresenter {
    private ExternalServicesView view;
    private String userID;

    public ExternalServicesPresenter(ExternalServicesView view, String userID){
        this.view = view;
        this.userID = userID;
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

    public List<String> getSupplyServices(){
        return APIcalls.getExternalSupplyServices();
    }

    public List<String> getPaymentServices(){
        return APIcalls.getExternalPaymentServices();
    }
}
