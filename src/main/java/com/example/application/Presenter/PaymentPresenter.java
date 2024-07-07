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
    private CartDTO cartDTO;

    public PaymentPresenter(PaymentView view, String userID, CartDTO cartDTO){
        this.view = view;
        this.userID = userID;
        this.cartDTO = cartDTO;
    }

    public void onSubmitButtonClicked(String holderID, String holderName, String currency,
                                      String creditCard, int cvv, int month, int year) {
        UserDTO userDTO = APIcalls.getUser(userID);
        PaymentDTO paymentDTO = new PaymentDTO(holderID, holderName, currency, creditCard, cvv, month, year);
        APIcalls.setUserConfirmationPurchase(userID);
        String result = APIcalls.purchase(userDTO,paymentDTO,cartDTO);
        view.paymentResult(result);
    }

}
