package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.View.MemberViews.OpenStoreView;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.server.VaadinSession;

public class OpenStorePresenter {
    private OpenStoreView view;
    private String userID;

    public OpenStorePresenter(OpenStoreView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public void onDoneButtonClicked(String storeName, String description) {
        if(APIcalls.openStore(userID, storeName, description) != null){
            view.openSuccess("Store was opened");
        }
        else{
            view.openFailure("Invalid input");
        }
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
