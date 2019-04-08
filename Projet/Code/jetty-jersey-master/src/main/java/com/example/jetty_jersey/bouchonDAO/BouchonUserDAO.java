package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;
import com.example.jetty_jersey.database.ClientDB;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BouchonUserDAO implements UserDAO {

    public boolean createUser(User user, Licence licence) {
        try {
            List<User> liste=JettyMain.c.allUser();
            for(int i=0; i<liste.size(); i++){
                if(liste.get(i).getEmail().equals(user.getEmail())) return false;
            }
            JettyMain.c.indexDB(user,licence);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean signInUser(User user) {
        try {
            List<User> liste=JettyMain.c.allUser();
            for(int i=0; i<liste.size();i++){
                System.out.println(liste.get(i));
                if(liste.get(i).getEmail().equals(user.getEmail()) && liste.get(i).getPassword().equals(DigestUtils.md5Hex(user.getPassword())))
                    return true;
            }
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

    public boolean updateUser(String userId) {
        try{
            //User u = new User("Boulet", "Reno", "popol@popol.com", "sgofhgoofhdg", "1994-08-01", "0606060606", "passenger");
            //JettyMain.c.updateUserInIndex(JettyMain.c.getUserById(userId));
            User u = JettyMain.c.getUserById(userId);
            u.setFirstName("Boulet");
            u.setLastName("Rama");
            u.setEmail("papalis@popol.com");
            u.setPassword("grrggff55");
            u.setBirthDate("1994-08-01");
            u.setGsm("065156023");
            u.setTypeUser("passenger");
            JettyMain.c.updateUserInIndex(u);
            //En dur, modifier pour modifier l'user avec les bonnes informations.
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String userId) {
        try{
            JettyMain.c.delete(userId, "user");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserDetails(String userId) {
        try {
            return JettyMain.c.getUserById(userId);
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