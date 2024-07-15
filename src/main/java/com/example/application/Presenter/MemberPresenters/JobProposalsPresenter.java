package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.*;
import com.example.application.View.MemberViews.JobProposalsView;
import com.example.application.WebSocketUtil.WebSocketHandler;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;

public class JobProposalsPresenter {
    private JobProposalsView view;
    private String userID;

    public JobProposalsPresenter(JobProposalsView view, String userID) {
        this.view = view;
        this.userID = userID;
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

    public List<StoreOwnerDTO> getStoreOwnerProposals() {
        return APIcalls.getOwnerJobProposal(userID);
    }

    public List<StoreManagerDTO> getStoreManagerProposals() {
        return APIcalls.getManagerJobProposal(userID);
    }

    public StoreDTO getStore(String storeID){
        return APIcalls.getStore(storeID);
    }

    public String getStoreName(String storeID){
        if(getStore(storeID) == null){
            return null;
        }
        return getStore(storeID).getStoreName();
    }

    public void answerProposal(String storeID, boolean manager_proposal, boolean answer){
        String result = APIcalls.answerJobProposal(userID, storeID, manager_proposal, answer);
        if (result.contains("success")) {
            view.answerSuccess("Thank you for your answer");
        } else {
            view.answerFailure(result);
        }
    }
}
