package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.AcquisitionDTO;
import com.example.application.Util.ReceiptDTO;
import com.example.application.View.MemberViews.HistoryView;
import com.vaadin.flow.server.VaadinSession;

import java.util.List;
import java.util.Map;

public class HistoryPresenter {
    private HistoryView view;
    private String userID;

    public HistoryPresenter(HistoryView view, String userID) {
        this.view = view;
        this.userID = userID;
    }

    public void loadAcquisitionHistory() {
        try {
            List<AcquisitionDTO> acquisitions = APIcalls.getUserAcquisitionsHistory(userID);
            view.showAcquisitions(acquisitions);
        } catch (Exception e) {
            view.showError("Failed to load acquisition history.");
        }
    }

    public void onAcquisitionSelected(String acquisitionId) {
        try {
            Map<String, ReceiptDTO> receipts = APIcalls.getUserReceiptsByAcquisition(userID, acquisitionId);
            view.showReceipts(receipts);
        } catch (Exception e) {
            view.showError("Failed to load receipt details.");
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

