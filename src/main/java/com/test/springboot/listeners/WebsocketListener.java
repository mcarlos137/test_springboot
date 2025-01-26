package com.silkrivercapital.springboot.listeners;

import java.io.IOException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.silkrivercapital.springboot.services.WebSocketSessionService;

public class WebsocketListener extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException, InterruptedException {
        Thread.sleep(300); // simulated delay
        JSONObject jsonObject = new JSONObject(message.getPayload());
        switch (jsonObject.get("method").toString()) {
            case "SUBSCRIBE":
                webSocketSessionService.subscribe(jsonObject.get("operation").toString(), jsonObject.get("channel").toString(), session);
                webSocketSessionService.sendMessage(session, "SUBSCRIBED TO ORDERBOOK CHANNEL " + jsonObject.get("channel").toString());
                break;
            case "UNSUBSCRIBE":
                webSocketSessionService.unsubscribe(jsonObject.get("operation").toString());
                webSocketSessionService.sendMessage(session, "UNSUBSCRIBED TO ORDERBOOK CHANNELS");
                break;
            default:
                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("CONNECTION ESTABLISHED");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionService.unsubscribe("orderBook");
        System.out.println("CONNECTION CLOSED");
    }

}