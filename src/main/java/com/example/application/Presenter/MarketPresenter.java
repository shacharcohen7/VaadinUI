package com.example.application.Presenter;

import com.example.application.Model.APIcalls;
import com.example.application.Model.MarketModel;
import com.example.application.Model.Product;
import com.example.application.Util.APIResponse;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.StoreDTO;
import com.example.application.View.MarketView;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class MarketPresenter {
    private MarketView view;
    private String userID;

    public MarketPresenter(MarketView view, String userID){
        this.view = view;
        this.userID = userID;
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

    public void onSearchButtonClicked(String productName, String category,
                                      Set<String> keywords, int minPrice, int maxPrice, int minStoreRating) {
        view.showGeneralSearchResult(APIcalls.generalProductSearch(userID, productName, category, new ArrayList<>(keywords)));
    }

    public List<StoreDTO> getAllStores(){
        return APIcalls.getAllStores();
    }

    public String getStoreName(String storeID){
        return APIcalls.getStore(storeID).getStoreName();
    }

    public boolean isAdmin(){
        return true;
    }

    public void logOut(){
        if(APIcalls.logout(userID).contains("success")){
            view.logout();
        }
    }

    public void onAddToCartButtonClicked(ProductDTO product, int quantity){
        //call AddProductCart()
        boolean success = MarketModel.addToCart();
        String message;
        if (success) {
            message = "Product was added to cart";
        } else {
            message = "Failed adding product to cart";
        }
        view.addProductCartResult(message);
    }
}
