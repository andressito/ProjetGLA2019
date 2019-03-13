package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.User;

import java.util.List;

public interface UserDAO {

    boolean createUser(User user);
    int signInUser(String email,String password);
    boolean updateUser(String userId);
    boolean deleteUser( String userId);
    User getUserDetails(String userId);
    List<User> getAllUser();
}
