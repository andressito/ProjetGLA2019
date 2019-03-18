package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;
import com.example.jetty_jersey.database.ClientDB;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BouchonUserDAO implements UserDAO {

    private List<User> liste= new ArrayList<User>();
    public boolean createUser(User user) {

        try {

            JettyMain.c.indexDB(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int signInUser(String email, String password) {

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
