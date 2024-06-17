package com.example.application.Model;

import com.example.application.Util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class APIcalls {
    static RestTemplate restTemplate = new RestTemplate();;
    static ObjectMapper mapper = new ObjectMapper();

    public static String startSession() {
        String url = "http://localhost:8080/api/market/enterSystem";  // Absolute URL

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept" , "*/*");
        try {
            URI uri = new URI(url);// Create URI object

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                APIResponse<String> responseBody = response.getBody();
                if (responseBody != null) {
                    String data = responseBody.getData();
                    if (data != null) {
                        return data;
                    }
                }
            } else {
                System.err.println("Error occurred: " + response.getStatusCode());
            }
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL: " + e.getMessage());
            // Handle the exception accordingly
        }
        return "notFound";
    }

    public static String init(UserDTO userDTO, String password, PaymentServiceDTO paymentServiceDTO, SupplyServiceDTO supplyServiceDTO) {
        try {
            String url = "http://localhost:8080/api/market/init";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("userDTO", mapper.writeValueAsString(userDTO))
                    .queryParam("password", password)
                    .queryParam("paymentServiceDTO", mapper.writeValueAsString(paymentServiceDTO))
                    .queryParam("supplyServiceDTO", mapper.writeValueAsString(supplyServiceDTO))
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        } catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static boolean checkInitializedMarket(){
        try {
            String url = "http://localhost:8080/api/market/checkInitializedMarket";  // Absolute URL

            URI uri = new URI(url);

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static boolean isMember(String userID){
        try {
            String url = "http://localhost:8080/api/member/isMember/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static String getMemberName(String memberID){
        try {
            String url = "http://localhost:8080/api/member/getMemberName/{memberId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(memberID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<StoreDTO> getAllStores(){
        try {
            String url = "http://localhost:8080/api/store/getAllStores";  // Absolute URL

            URI uri = new URI(url);

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            List<StoreDTO> data = new LinkedList<StoreDTO>();
            for(int i=0 ; i<responseBody.getData().size() ; i++){
                data.add(mapper.readValue(responseBody.getData().get(i), StoreDTO.class));
            }
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static CartDTO getCart(String userID){
        try {
            String url = "http://localhost:8080/api/user/getCart/{id}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            return mapper.readValue(responseBody.getData(), CartDTO.class);
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<String> getCategories(){
        try {
            String url = "http://localhost:8080/api/market/getCategories";  // Absolute URL

            URI uri = new URI(url);

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            return responseBody.getData();
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<ProductDTO> getStoreProducts(String storeID){
        try {
            String url = "http://localhost:8080/api/market/getStoreProducts/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            List<ProductDTO> data = new LinkedList<ProductDTO>();
            for(int i=0 ; i<responseBody.getData().size() ; i++){
                data.add(mapper.readValue(responseBody.getData().get(i), ProductDTO.class));
            }
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<String> getStoreOwners(String storeID){
        try {
            String url = "http://localhost:8080/api/market/getStoreOwners/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            return responseBody.getData();
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<String> getStoreMangers(String storeID){
        try {
            String url = "http://localhost:8080/api/market/getStoreManagers/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            return responseBody.getData();
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static StoreDTO getStore(String storeID){
        try {
            String url = "http://localhost:8080/api/store/getStore/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            StoreDTO data = mapper.readValue(responseBody.getData(), StoreDTO.class);
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static UserDTO getUser(String userID){
        try {
            String url = "http://localhost:8080/api/user/getUser/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            UserDTO data = mapper.readValue(responseBody.getData(), UserDTO.class);
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String register(UserDTO userDTO, String password) {
        try {
            String url = "http://localhost:8080/api/market/register";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url).queryParam("userDTO", mapper.writeValueAsString(userDTO))
                    .queryParam("password", password).build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        } catch (HttpClientErrorException e) {
            // Extract error message from the response body
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }

    public static Map<String, List<ProductDTO>> generalProductSearch(String userID, String productName, String categoryStr, List<String> keywords){
        try {
            String url = "http://localhost:8080/api/market/generalProductSearch/{userId}/{productName}/{categoryStr}/{keywords}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, productName, categoryStr, keywords)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Map<String, List<String>>>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Map<String, List<String>>>>() {
                    });
            APIResponse<Map<String, List<String>>> responseBody = response.getBody();
            Map<String,List<ProductDTO>> data = new HashMap<>(); //<storeid, list<product>>
            for (Map.Entry<String, List<String>> entry : responseBody.getData().entrySet()) {
                List<ProductDTO> productsDTOs = new ArrayList<>();
                for (String productJson : entry.getValue()) {
                    ProductDTO productDTO = mapper.readValue(productJson, ProductDTO.class);
                    productsDTOs.add(productDTO);
                }
                data.put(entry.getKey(), productsDTOs);
            }
            return data;
        } catch (HttpClientErrorException e) {
            throw new APIException(extractErrorMessageFromJson(e.getResponseBodyAsString()));
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<ProductDTO> inStoreProductSearch(String userID, String productName, String categoryStr, List<String> keywords, String storeId){
        try {
            String url = "http://localhost:8080/api/market/inStoreProductSearch/{userId}/{productName}/{categoryStr}/{keywords}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, productName, categoryStr, keywords, storeId)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<List<String>>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<List<String>>>() {
                    });
            APIResponse<List<String>> responseBody = response.getBody();
            List<ProductDTO> data = new LinkedList<ProductDTO>();
            for(int i=0 ; i<responseBody.getData().size() ; i++){
                data.add(mapper.readValue(responseBody.getData().get(i), ProductDTO.class));
            }
            return data;
        }
        catch (HttpClientErrorException e) {
            throw new APIException(extractErrorMessageFromJson(e.getResponseBodyAsString()));
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static boolean isStoreOwner(String memberID, String storeID){
        try {
            String url = "http://localhost:8080/api/member/isStoreOwner/{memberId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(memberID, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static boolean isStoreManager(String memberID, String storeID){
        try {
            String url = "http://localhost:8080/api/member/isStoreManager/{memberId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(memberID, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static boolean hasInventoryPermission(String memberID, String storeID){
        try {
            String url = "http://localhost:8080/api/member/hasInventoryPermission/{memberId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(memberID, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static boolean hasPurchasePermission(String memberID, String storeID){
        try {
            String url = "http://localhost:8080/api/member/hasPurchasePermission/{memberId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(memberID, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<Boolean>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<Boolean>>() {
                    });
            APIResponse<Boolean> responseBody = response.getBody();
            Boolean data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return false;
        }
    }

    public static String logout(String userID){
        try {
            String url = "http://localhost:8080/api/market/logout/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String login(String userID, String username, String password){
        try {
            String url = "http://localhost:8080/api/market/login/{userId}/{userName}/{password}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, username, password)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String closeStore(String userID, String storeID){
        try {
            String url = "http://localhost:8080/api/market/closeStore/{userId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String openStore(String userID, String storeName, String storeDes){
        try {
            String url = "http://localhost:8080/api/market/openStore/{userId}/{storeName}/{storeDes}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, storeName, storeDes)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String addProductToBasket(String productName, int quantity, String storeID, String userID){
        try {
            String url = "http://localhost:8080/api/market/addProductToBasket/{productName}/{quantity}/{storeId}/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(productName, quantity, storeID, userID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String addProductToStore(String userID, String storeID, ProductDTO productDTO){
        try {
            String url = "http://localhost:8080/api/market/addProductToStore";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("userId", userID)
                    .queryParam("storeId",storeID)
                    .queryParam("productDTO",mapper.writeValueAsString(productDTO))
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String removeProductFromStore(String userID, String storeID, String productName){
        try {
            String url = "http://localhost:8080/api/market/removeProductFromStore/{userId}/{storeId}/{productId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, storeID, productName)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.DELETE,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String updateProductInStore(String userID, String storeID, ProductDTO productDTO){
        try {
            String url = "http://localhost:8080/api/market/updateProductInStore";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("userId", userID)
                    .queryParam("storeId",storeID)
                    .queryParam("productDTO",mapper.writeValueAsString(productDTO))
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String appointStoreOwner(String userID, String appointedUsername, String storeID){
        try {
            String url = "http://localhost:8080/api/market/appointStoreOwner/{userId}/{appointedId}/{storeId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, appointedUsername, storeID)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String appointStoreManager(String userID, String appointedUsername, String storeID, boolean invPer, boolean purPer){
        try {
            String url = "http://localhost:8080/api/market/appointStoreManager/{userId}/{appointedId}/{storeId}/{invPer}/{purPer}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, appointedUsername, storeID, invPer, purPer)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String updateStoreManagerPermissions(String userID, String appointedUsername, String storeID, boolean invPer, boolean purPer){
        try {
            String url = "http://localhost:8080/api/market/updateStoreManagerPermissions/{userId}/{appointedId}/{storeId}/{invPer}/{purPer}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, appointedUsername, storeID, invPer, purPer)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String removeProductFromBasket(String productName, String storeId,String userId){
        try {
            String url = "http://localhost:8080/api/market/removeProductFromBasket/{productName}/{storeId}/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(productName, storeId, userId)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.DELETE,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static String modifyShoppingCart(String productName, int quantity,String storeId,String userId){
        try {
            String url = "http://localhost:8080/api/market/modifyShoppingCart/{productName}/{quantity}/{storeId}/{userId}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(productName, quantity,storeId, userId)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        }
        catch (HttpClientErrorException e){
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static CartDTO getCartAfterValidation(String userID, UserDTO userDTO){
        try {
            String url = "http://localhost:8080/api/market/getCartAfterValidation";  // Absolute URL todo change this according nadav

            URI uri = UriComponentsBuilder.fromUriString(url).queryParam("userID", userID)
                    .queryParam("userDTO", mapper.writeValueAsString(userDTO)).build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            return mapper.readValue(responseBody.getData(), CartDTO.class);
        }
         catch (HttpClientErrorException e) {
            throw new APIException(extractErrorMessageFromJson(e.getResponseBodyAsString()));
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    //todo change this after nadav doing the function.
    public static String purchase(UserDTO userDTO, PaymentDTO paymentDTO,CartDTO cartDTO){
        try {
            String url = "http://localhost:8080/api/market//purchase";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url).queryParam("userDTO", mapper.writeValueAsString(userDTO))
                    .queryParam("paymentDTO", mapper.writeValueAsString(paymentDTO))
                    .queryParam("cartDTO", mapper.writeValueAsString(cartDTO)).build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.add("accept", "*/*");
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(
                    uri,  // Use the URI object here
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {
                    });
            APIResponse<String> responseBody = response.getBody();
            String data = responseBody.getData();
            return data;
        } catch (HttpClientErrorException e) {
            // Extract error message from the response body
            return extractErrorMessageFromJson(e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }



    private static String extractErrorMessageFromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode errorMessageNode = rootNode.path("errorMassage");
            return errorMessageNode.asText("An unknown error occurred.");
        } catch (Exception e) {
            e.printStackTrace();
            return "An unknown error occurred.";
        }
    }
}
