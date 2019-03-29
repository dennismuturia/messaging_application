package com.dennis.messaging.controller;

import com.dennis.messaging.model.Chat_Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat_Message sendMessage(@Payload Chat_Message chat_message){
        return chat_message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public  Chat_Message addUser(@Payload Chat_Message chat_message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", chat_message.getSender());
        return chat_message;
    }
}
