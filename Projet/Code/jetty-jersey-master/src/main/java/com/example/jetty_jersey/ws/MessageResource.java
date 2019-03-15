package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/message")
public class MessageResource {



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createMessage() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{messageId}")
    public Message getMessage(@PathParam("messageId") String messageId) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/user/{userId}")
    public List<Message> getMessageUser(@PathParam("userId") String userId) {
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{messageId}")
    public List<Message> deleteMessage(@PathParam("messageId") String messageId){
        return null;
    }
}
