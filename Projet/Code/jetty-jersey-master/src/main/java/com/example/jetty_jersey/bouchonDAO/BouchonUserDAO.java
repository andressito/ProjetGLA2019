package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
            User user= JettyMain.c.getUserByEmail(email);
            user.toStringUser();
            return user;
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