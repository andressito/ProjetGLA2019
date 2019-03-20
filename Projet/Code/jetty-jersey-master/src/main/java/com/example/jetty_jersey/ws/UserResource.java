package com.example.jetty_jersey.ws;
import com.example.jetty_jersey.bouchonDAO.BouchonUserDAO;
import com.example.jetty_jersey.classes.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/user")
public class UserResource {
    public BouchonUserDAO buDAO = new BouchonUserDAO();

    @Context private HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users")
    public List<User> getAllUser() {
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/create")
    public String createUser(@FormParam("profile") String profile,@FormParam("firstName") String fistName,
                             @FormParam("lastName") String lastName,@FormParam("birthDate")String birthDate,
                             @FormParam("email") String email, @FormParam("password") String password,
                             @FormParam("passwordConfirm") String passwordConfirm) {
        if(!password.equals(passwordConfirm)){
            return "{\"error\" : \"password not matching\" }";
        }else{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if(birthDate==null) return "{\"response\" : \"error date\" }";
            User user = new User(fistName,lastName,email, DigestUtils.md5Hex(password),birthDate,null);
            return "{\"response\" : \""+buDAO.createUser(user)+"\" }";
        }

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/signin")
    public String signIn(@FormParam("email") String email, @FormParam("password") String password) {
        int res = buDAO.signInUser(email,password);
        if(res==-1){
            return "{\"error\" : \"email or password wrong\" }";
        }else{
            request.getSession(true);

            return "{\"success\" : \"You are logged in\"} ";
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public User getUser(@PathParam("idUser") String id) {

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

        return  null;
    }
}
