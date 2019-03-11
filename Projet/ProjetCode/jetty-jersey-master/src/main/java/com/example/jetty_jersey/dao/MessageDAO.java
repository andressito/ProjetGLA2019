package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Message;

import java.util.List;

public interface MessageDAO {
    boolean addMessage(Message message);
    boolean deleteMessage(String messageId);
    List<Message> getUserMessage(String userId);
    Message getMessageDetails(String messageId);

}
