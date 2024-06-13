package com.example.application.Presenter.MemberPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.UserDTO;
import com.example.application.View.MemberViews.MyProfileView;

import java.time.LocalDate;

public class MyProfilePresenter {
    private MyProfileView view;
    private String userID;

    public MyProfilePresenter(MyProfileView view, String userID){
        this.view = view;
        this.userID = userID;
    }

    public UserDTO getUser(){
        return APIcalls.getUser(userID);
    }

    public void onSaveButtonClicked(LocalDate birthdate, String country, String city,
                                      String address, String name) {
        //call updateUser()
        boolean success = true;
        if (success){
            view.updateSuccess("User details were updated successfully");
        } else {
            view.updateFailure("Invalid details.");
        }
    }
}
