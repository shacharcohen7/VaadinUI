package com.example.application.Presenter;

import com.example.application.Model.APIcalls;
import com.example.application.Util.PaymentServiceDTO;
import com.example.application.Util.SupplyServiceDTO;
import com.example.application.Util.UserDTO;
import com.example.application.View.InitializeView;

public class InitializePresenter {
    InitializeView view;

    public InitializePresenter(InitializeView view){
        this.view = view;
        action();
    }

    public void action(){
        if(APIcalls.checkInitializedMarket()){
            view.startSession();
        }
        else{
            view.buildView();
        }
    }

    public void onInitializeButtonClicked(UserDTO userDTO, String password, PaymentServiceDTO paymentServiceDTO, SupplyServiceDTO supplyServiceDTO){
        if(APIcalls.init(userDTO, password, paymentServiceDTO, supplyServiceDTO).contains("success")){
            view.startSession();
        }
    }
}
