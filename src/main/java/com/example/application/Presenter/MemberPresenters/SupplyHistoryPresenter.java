package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.AcquisitionDTO;
import com.example.application.Util.ReceiptDTO;
import com.example.application.Util.ShippingDTO;
import com.example.application.View.MemberViews.PurchaseHistoryView;
import com.example.application.View.MemberViews.SupplyHistoryView;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.server.VaadinSession;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplyHistoryPresenter {
    private SupplyHistoryView view;
    private String userID;

    public SupplyHistoryPresenter(SupplyHistoryView view, String userID) {
        this.view = view;
        this.userID = userID;
    }

    public List<ShippingDTO> loadSupplyHistory() {
//        return APIcalls.getUserAcquisitionsHistory(userID);
        List<ShippingDTO> list = new LinkedList<>();
        ShippingDTO dto = new ShippingDTO("a", 1, "a", "a", "a", "a", "a");
        list.add(dto);
        return list;
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

    public String getUserName(){
        if(isMember()){
            return APIcalls.getMemberName(VaadinSession.getCurrent().getAttribute("memberID").toString());
        }
        return "Guest";
    }

    public boolean isMember(){
        return APIcalls.isMember(userID);
    }

}
