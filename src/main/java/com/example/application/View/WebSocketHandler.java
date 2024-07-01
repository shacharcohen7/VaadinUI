package com.example.application.View;



import com.vaadin.flow.server.VaadinSession;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;


import java.net.URI;
import java.util.concurrent.CompletableFuture;


public class WebSocketHandler {
    private WebSocketClient webSocketClient;
    private  ClientEndPoint clientEndPoint;

    public void openConnection(){
        try {
            HttpClient httpClient = new HttpClient();
            webSocketClient = new WebSocketClient(httpClient);
            webSocketClient.start();
            ClientEndPoint clientEndPoint = new ClientEndPoint();
            //clientEndPoint.addMessageListener(messageListener);
//            String username = Credentials.getUserName();
            String userId = VaadinSession.getCurrent().getAttribute("userID").toString();;
            URI serverURI = URI.create("ws://localhost:8080/ws?userID=" + userId);
            CompletableFuture<Session> clientSessionPromise = webSocketClient.connect(clientEndPoint, serverURI);
            clientSessionPromise.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
