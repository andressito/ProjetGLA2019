package com.example.jetty_jersey.douchonDao;

import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;

import java.util.List;

public class BouchonUserDAO implements UserDAO {
    public boolean createUser(User user) {
        return false;
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
