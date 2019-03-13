package com.example.jetty_jersey.ws;
import com.example.jetty_jersey.classes.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/user")
public class UserResource {

    public List<User> remplirList(){
        List<User> all = new ArrayList<User>();
        User u1 = new User("ID125","ANDRE","KENY","ANDREKENY@GMAIL.COM","0712567889");
        User u2 = new User("ID126","HIBA","ALAMI","HIBAALAMI@GMAIL.COM","0713457896");
        User u3= new User("ID127","DJAGOU","DONKO","DJAGOUDONKO@GMAIL.COM","0789579897");
        all.add(u1);
        all.add(u2);
        all.add(u3);
        return all;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<User> getAllUser() {
        return remplirList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/create")
    public String createUser(@FormParam("profile") String profile, @FormParam("firstName") String fistName,
                             @FormParam("lastName") String lastName,/* @FormParam("dateBirth")Date dateBirth,*/
                             @FormParam("email") String email, @FormParam("password") String password,
                             @FormParam("passwordConfirm") String passwordConfirm) {

        return "{ \"foo\": 2}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public User getUser(@PathParam("idUser") String id) {
        List<User> all = remplirList();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdUser().equals(id)) return all.get(i);
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public String updateUser(@PathParam("idUser") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public List<User> deleteUser(@PathParam("idUser") String id){
        List<User> all = remplirList();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdUser().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
