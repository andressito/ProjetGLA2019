package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
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

    public String createUser(User user) {
        try {
            List<User> liste=JettyMain.c.allUser();
            for(int i=0; i<liste.size(); i++){
                if(liste.get(i).getEmail().equals(user.getEmail())) return "mail used";
            }
            JettyMain.c.indexDB(user);
            return "User add";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "error add";
    }

    public int signInUser(String email, String password) {
        try {
            List<User> liste=JettyMain.c.allUser();
            for(int i=0; i<liste.size();i++){
                System.out.println(liste.get(i).getPassword());
                if(liste.get(i).getEmail().equals(email) && liste.get(i).getPassword().equals(DigestUtils.md5Hex(password)))
                    return 1;
            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean updateUser(String userId) {
        return false;
    }

    public boolean deleteUser(String userId) {
        return false;
    }

    public User getUserDetails(String userId) {
        return null;
    }

    public List<User> getAllUser() {
        return null;
    }
}
