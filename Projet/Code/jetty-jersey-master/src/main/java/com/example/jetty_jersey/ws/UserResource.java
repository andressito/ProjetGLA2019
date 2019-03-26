package com.example.jetty_jersey.ws;
import com.example.jetty_jersey.bouchonDAO.BouchonUserDAO;
import com.example.jetty_jersey.classes.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserResource {
    public BouchonUserDAO buDAO = new BouchonUserDAO();

    @Context private HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<User> getAllUser() {
        return buDAO.getAllUser();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createUser(User user) {
        String mdp = user.getPassword();
        user.setPassword(DigestUtils.md5Hex(mdp));
        return buDAO.createUser(user);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signin")
    public boolean signIn(User user) {
        return buDAO.signInUser(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public User getUser(@PathParam("idUser") String id) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/useremail")
    public User getUserByEmail(User user){
        return buDAO.getUserByEmail(user);
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

        return  null;
    }
}
