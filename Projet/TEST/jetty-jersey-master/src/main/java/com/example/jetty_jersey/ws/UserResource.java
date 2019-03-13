package com.example.jetty_jersey.ws;
import com.example.jetty_jersey.bouchonDAO.BouchonUserDAO;
import com.example.jetty_jersey.classes.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/user")
public class UserResource {
    public BouchonUserDAO buDAO = new BouchonUserDAO();
    public List<User> remplirList(){
       /* List<User> all = new ArrayList<User>();
        User u1 = new User("ID125","ANDRE","KENY","ANDREKENY@GMAIL.COM","0712567889");
        User u2 = new User("ID126","HIBA","ALAMI","HIBAALAMI@GMAIL.COM","0713457896");
        User u3= new User("ID127","DJAGOU","DONKO","DJAGOUDONKO@GMAIL.COM","0789579897");
        all.add(u1);
        all.add(u2);
        all.add(u3);
        return all;*/
       return null;
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
    public String createUser(@FormParam("profile") String profile,@FormParam("firstName") String fistName,
                             @FormParam("lastName") String lastName,@FormParam("birthDate")String birthDate,
                             @FormParam("email") String email, @FormParam("password") String password,
                             @FormParam("passwordConfirm") String passwordConfirm) {
        if(!password.equals(passwordConfirm)){
            return "{\"error\" : \"password not matching\" }";
        }else{
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            if(birthDate==null) return "{\"error\" : \"error date\" }";
            try {
                date=df.parse(birthDate);
                User user = new User(001,fistName,lastName,email,password,date,null);
                if(buDAO.createUser(user)) return "{\"success\" : \"new user added\"} ";
                else return "{\"error\" : \"email used\" }";
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }
        return null;

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
            // connexion réussi
        }
        return null;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public User getUser(@PathParam("idUser") String id) {
        List<User> all = remplirList();
        for (int i=0; i<all.size();i++){
            //if( all.get(i).getIdUser().equals(id)) return all.get(i);
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
           /* if( all.get(i).getIdUser().equals(id)){
                all.remove(all.get(i));
                return all;
            }*/
        }
        return  all;
    }
}
