package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonUserDAO;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.List;

@Path("/user")
public class UserResource {
    private static final String salt = "&é*-;";
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
    public boolean createUser(Object o) throws NoSuchAlgorithmException, NoSuchProviderException {
        String fistName=null,lastName=null,birthDate=null,email=null,type=null,password=null,gsm=null;
        String validityDate=null;
        String test = o.toString();
        test=test.replace("\"","\\\"");
        test=test.replace("user={","\"user\":\"{");
        test=test.replace("licence={","\"licence\":\"{");
        test=test.replace(", \"licence"," \" ,\"licence");
        test=test.replace("}}","} \" }");
        JSONObject testJson = new JSONObject(test);
        String user = testJson.getString("user");
        user=user.replace("\"","");
        user=user.replace(" ","");
        user=user.replace("{","");
        user=user.replace("}","");
        String licence= testJson.getString("licence");
        licence=licence.replace("\"","");
        licence=licence.replace(" ","");
        licence=licence.replace("{","");
        licence=licence.replace("}","");
        String[] tabUser = user.split(",");
        String[] tabLicence= licence.split(",");
        for(int i=0; i<tabUser.length;i++){
            String[] val=tabUser[i].split(":");
            if ("firstName".equals(val[0])) {
                fistName = val[1];
            } else if ("lastName".equals(val[0])) {
                lastName = val[1];
            } else if ("birthDate".equals(val[0])) {
                birthDate = val[1];
            } else if ("email".equals(val[0])) {
                email = val[1];
            } else if ("type".equals(val[0])) {
                type = val[1];
            } else if ("gsm".equals(val[0])) {
                gsm = val[1];
            } else if ("password".equals(val[0])) {
                password = val[1];
            }
        }
        for(int i=0; i<tabLicence.length;i++){
            String[] val=tabLicence[i].split(":");
            if(val[0].equals("validityDate")) validityDate=val[1];

        }
        User newUser = new User(fistName,lastName,email,executeSaltMD5(password),birthDate,gsm,type);
        if(newUser.typeUser.equals("passenger")) return buDAO.createUser(newUser,null);
        else{
            Licence licence1= new Licence(null,null,validityDate,0,0);
            return buDAO.createUser(newUser,licence1);
        }
        //return false;
    }

    public static String executeSaltMD5(String passwordToHash) throws NoSuchAlgorithmException, NoSuchProviderException {
        String machin=passwordToHash+salt;
        return DigestUtils.md5Hex(machin);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signin")
    public boolean signIn(User user) throws NoSuchAlgorithmException, NoSuchProviderException {
        return buDAO.signInUser(user);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{idUser}")
    public User getUser(@PathParam("idUser") String id) {
        return buDAO.getUserDetails(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/email/{emailUser}")
    public User getUserByEmail(@PathParam("emailUser") String emailUser){
        return buDAO.getUserByEmail(emailUser);
    }



    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/update")
    public boolean updateUser(User user){
        return buDAO.updateUser(user);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{idUser}")
    public boolean deleteUser(@PathParam("idUser") String id){
        return buDAO.deleteUser(id);
    }
}
