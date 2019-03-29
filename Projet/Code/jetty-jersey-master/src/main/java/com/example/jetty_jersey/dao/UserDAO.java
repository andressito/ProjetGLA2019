package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;

import java.util.List;

public interface UserDAO {

    boolean createUser(User user, Licence licence);
    boolean signInUser(User user);
    boolean updateUser(String userId);
    boolean deleteUser( String userId);
    User getUserDetails(String userId);
    List<User> getAllUser();
}
