package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Message;
import com.example.jetty_jersey.dao.MessageDAO;

import java.util.List;

public class BouchonMessageDAO implements MessageDAO {

    public boolean addMessage(Message message){
        try {
            JettyMain.c.indexDB(message, null);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMessage(String messageId){
        try {
            JettyMain.c.delete(messageId, "message");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Message> getUserMessage(String userId){
        try {
            return JettyMain.c.getMessageByUserId(userId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Message getMessageDetails(String messageId){
        try {
            JettyMain.c.getMessageByMessageId(messageId);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}