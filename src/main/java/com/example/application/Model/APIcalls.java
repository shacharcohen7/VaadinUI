package com.example.application.Model;

import com.example.application.Util.APIResponse;
import com.example.application.Util.ProductDTO;
import com.example.application.Util.StoreDTO;
import com.example.application.Util.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static String register(UserDTO userDTO, String password){
        try {
            String url = "http://localhost:8080/api/market/register/{userDTO}/{password}";  // Absolute URL

//            URI uri = UriComponentsBuilder.fromUriString(url)
//                    .buildAndExpand(mapper.writeValueAsString(userDTO), password)
//                    .toUri();

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .queryParam("userDTO", mapper.writeValueAsString(userDTO))
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
        }
        catch (Exception e){
            System.err.println("error occurred");
            return null;
        }
    }

    public static List<ProductDTO> generalProductSearch(String userID, String productName, String categoryStr, List<String> keywords){
        try {
            String url = "http://localhost:8080/api/market/generalProductSearch/{userId}/{productName}/{categoryStr}/{keywords}";  // Absolute URL

            URI uri = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(userID, productName, categoryStr, keywords)
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
                    HttpMethod.GET,
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
                    HttpMethod.GET,
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
                    HttpMethod.GET,
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
}
