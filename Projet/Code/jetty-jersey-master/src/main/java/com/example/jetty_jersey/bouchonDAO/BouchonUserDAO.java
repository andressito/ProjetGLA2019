package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;
import com.example.jetty_jersey.ws.UserResource;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.List;

public class BouchonUserDAO implements UserDAO {

    public boolean createUser(User user, Licence licence) {
        try {
            if(JettyMain.c.getByFieldValue("user","email",user.getEmail()).length!=0)
                return false;
            return JettyMain.c.indexDB(user,licence);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean signInUser(User user) throws NoSuchAlgorithmException, NoSuchProviderException  {
        try {
            return JettyMain.c.canConnect(user.getEmail(), UserResource.executeSaltMD5(user.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByEmail(String email) {
        try {
            return JettyMain.c.getUserByEmail(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        try{
            if(user.getUserId()!= null && user.getTypeUser()!=null && user.getFirstName()==null){
                JettyMain.c.updateUserBecomePilot(user.getUserId(),"pilot");
            }
            JettyMain.c.updateUserInIndex(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        try{
            if(JettyMain.c.ifUserAPilot(userId)) {
                Licence l = JettyMain.c.getLicenceByUserId(userId);
                JettyMain.c.delete(l.licenceId, "licence");
                JettyMain.c.delete(userId, "user");
                return true;
            }
            JettyMain.c.delete(userId, "user");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserDetails(String userId) {
        try {
            return JettyMain.c.getUserByUserId(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUser() {
        try{
            return JettyMain.c.allUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}