package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonMessageDAO;
import com.example.jetty_jersey.classes.Message;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/message")
public class MessageResource {

    public BouchonMessageDAO bmDAO = new BouchonMessageDAO();

    @Context
    private HttpServletRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createMessage(Message message) {
        return bmDAO.addMessage(message);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{messageId}")
    public Message getMessage(@PathParam("messageId") String messageId) {
        return bmDAO.getMessageDetails(messageId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/user/{userId}")
    public List<Message> getMessageUser(@PathParam("userId") String userId) {
        return bmDAO.getUserMessage(userId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{messageId}")
    public boolean deleteMessage(@PathParam("messageId") String messageId){
        return bmDAO.deleteMessage(messageId);
    }
}
