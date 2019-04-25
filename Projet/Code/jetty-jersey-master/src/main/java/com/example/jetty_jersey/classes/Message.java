package com.example.jetty_jersey.classes;

import java.util.Date;

public class Message {
    public String messageId;
    public String content;
    public String senderId;
    public String receiverId;
    public String sendingDate;

    public Message() {}
    public Message(String content, String senderId, String receiverId, String sendingDate){
        this.messageId= null;
        this.content=content;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.sendingDate=sendingDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }
}
