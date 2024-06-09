package com.example.application.Presenter;

import com.example.application.Util.APIResponse;
import com.example.application.View.StartSessionView;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Wrapper;
import java.util.Collections;


public class StartSessionPresenter {
    private String userID;
    private StartSessionView view;
    RestTemplate restTemplate;

    public StartSessionPresenter(StartSessionView view) {
        this.view = view;
        this.restTemplate = new RestTemplate();
        startSession();

    }

    private void startSession() {
        String url = "http://localhost:8080/api/user";  // Absolute URL

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept" , "*/*");
        try {
            URI uri = new URI(url);// Create URI object

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<APIResponse<String>> response = restTemplate.exchange(uri,  // Use the URI object here
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<APIResponse<String>>() {});

            System.out.println("nadav");
            if (response.getStatusCode().is2xxSuccessful()) {
                APIResponse<String> responseBody = response.getBody();
                if (responseBody != null) {
                    String data = responseBody.getData();
                    if (data != null) {
                        userID = data;
                    }
                }
            } else {
                System.err.println("Error occurred: " + response.getStatusCode());
            }
            view.startSession(userID);
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL: " + e.getMessage());
            // Handle the exception accordingly
        }
    }

}
