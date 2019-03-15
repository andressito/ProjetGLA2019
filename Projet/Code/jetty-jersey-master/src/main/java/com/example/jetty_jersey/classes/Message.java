package com.example.jetty_jersey.classes;

import java.util.Date;

public class Message {
    public String messageId;
    public String content;
    public String senderId;
    public String receiverId;
    public Date sendingDate;

    public Message(String messageId,String content, String senderId, String receiverId, Date sendingDate){
        this.messageId= messageId;
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

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }
}
