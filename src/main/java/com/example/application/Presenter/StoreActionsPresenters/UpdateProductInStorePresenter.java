package com.example.application.Presenter.StoreActionsPresenters;

import com.example.application.Model.APIcalls;
import com.example.application.Util.ProductDTO;
import com.example.application.View.StoreActionsViews.AddProductToStoreView;
import com.example.application.View.StoreActionsViews.UpdateProductInStoreView;

import java.util.LinkedList;
import java.util.List;

public class UpdateProductInStorePresenter {
    private UpdateProductInStoreView view;
    private String userID;
    private String storeID;

    public UpdateProductInStorePresenter(UpdateProductInStoreView view, String userID, String storeID){
        this.view = view;
        this.userID = userID;
        this.storeID = storeID;
    }

    public List<String> getAllProductNames(){
        List<ProductDTO> products = APIcalls.getStoreProducts(storeID);
        List<String> productNames = new LinkedList<String>();
        for(int i=0 ;i<products.size() ; i++){
            productNames.add(products.get(i).getName());
        }
        return productNames;
    }

    public ProductDTO getProductByName(String productName){
        List<ProductDTO> products = APIcalls.getStoreProducts(storeID);
        for(int i=0 ;i<products.size() ; i++){
            if(products.get(i).getName().equals(productName)) {
                return products.get(i);
            }
        }
        return null;
    }

    public List<String> getCategories(){
        return APIcalls.getCategories();
    }

    public void onUpdateButtonClicked(String productName, int price, int quantity, String description, String category) {
        if (APIcalls.updateProductInStore(userID, storeID, new ProductDTO(productName, price, quantity, description, category)).contains("success")) {
            view.updateSuccess("Product was updated");
        } else {
            view.updateFailure("Invalid input.");
        }
    }
}
