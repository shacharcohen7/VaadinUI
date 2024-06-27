package com.example.application.Presenter;

import com.example.application.Model.APIcalls;
import com.example.application.Util.CartDTO;
import com.example.application.Util.PaymentDTO;
import com.example.application.Util.UserDTO;
import com.example.application.View.GuestViews.SignInView;
import com.example.application.View.PaymentView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentPresenter {
    private PaymentView view;
    private String userID;

    public PaymentPresenter(PaymentView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public void onSubmitButtonClicked(String creditCard, int cvv, int month, int year, String holderID) {
        UserDTO userDTO = APIcalls.getUser(userID);
        PaymentDTO paymentDTO = new PaymentDTO(holderID,creditCard,cvv,month,year);
        CartDTO cartDTO = APIcalls.getCartAfterValidation(userID);
        APIcalls.setUserConfirmationPurchase(userID);
        String result = APIcalls.purchase(userDTO,paymentDTO,cartDTO);
        view.paymentResult(result);
    }

}
