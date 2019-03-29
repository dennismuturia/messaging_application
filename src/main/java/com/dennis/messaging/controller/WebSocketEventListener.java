package com.dennis.messaging.controller;

import com.dennis.messaging.model.Chat_Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketEventListener {
    //Initialize the logging class
    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener (SessionConnectedEvent event){
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username !=null){
            log.info("User Disconnected: " + username);
            Chat_Message chat_message = new Chat_Message();
            chat_message.setType(Chat_Message.MessageType.LEAVE);
            chat_message.setSender(username);
            messagingTemplate.convertAndSend("/topic/public", chat_message);
        }
    }
}
