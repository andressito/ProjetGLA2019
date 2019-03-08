package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/message")
public class MessageResource {

    public List<Message> remplirListeMessage(){
        List<Message> liste= new ArrayList<Message>();
        Message m1 = new Message("M123","juste un test","ID125","ID126", new Date());
        Message m2 = new Message("M124","juste un autre test","ID125","ID127", new Date());
        Message m3 = new Message("M125","juste encre un autre test","ID125","ID127", new Date());
        Message m4 = new Message("M126","et encore juste un test","ID126","ID127", new Date());
        liste.add(m1);
        liste.add(m2);
        liste.add(m3);
        liste.add(m4);
        return liste;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createMessage() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{idMessage}")
    public Message getMessage(@PathParam("idMessage") String id) {
        List<Message> all = remplirListeMessage();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdMessage().equals(id)) return all.get(i);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/user/{idUser}")
    public List<Message> getMessageUser(@PathParam("idUser") String id) {
        List<Message> all = remplirListeMessage();
        List<Message> res= new ArrayList<Message>();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdDestinataire().equals(id))  res.add(all.get(i));
        }
        return res;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/messages/{idMessage}")
    public List<Message> deleteMessage(@PathParam("idMessage") String id){
        List<Message> all = remplirListeMessage();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdMessage().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
