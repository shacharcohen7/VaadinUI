package com.example.application.View;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import java.time.Duration;



public class ClientEndPoint implements WebSocketListener {
    Session session ;

    @Override
    public void onWebSocketError(Throwable cause) {
        //WebSocketListener.super.onWebSocketError(cause);
        System.out.println(cause.getCause().toString());
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        WebSocketListener.super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        session.setIdleTimeout(Duration.ZERO);
        //

        //WebSocketListener.super.onWebSocketConnect(session);
    }

    @Override
    public void onWebSocketText(String message) {
        System.out.println("Received message: " + message);
        //notifyMessageListeners(message);
        UI.getCurrent().access(() -> Notification.show(message));
        WebSocketListener.super.onWebSocketText(message);
    }
}
