package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.UserDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BouchonUserDAO implements UserDAO {

    List<User> liste= new ArrayList<User>();

    public void remplirBDUser(){
        User u1= new User("ID102","andrew","kenzo","ak@yopmail.com","passer",new Date(),"078955465");
        User u2= new User("ID103","kenzo","diouf","kd@yopmail.com","passer",new Date(),"078955785");
        User u3= new User("ID104","andressito","diop","ad@yopmail.com","passer",new Date(),"078954465");
        User u4= new User("ID105","andre","ndiaye","an@yopmail.com","passer",new Date(),"078375465");
        liste.add(u1);
        liste.add(u2);
        liste.add(u3);
        liste.add(u4);


    }

    public boolean createUser(User user) {
        remplirBDUser();
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getEmail().equals(user.getEmail())) return false;
        }
        liste.add(user);
        return true;
    }

    public int signInUser(String email, String password) {
        remplirBDUser();
        for(int i =0; i< liste.size(); i++){
            if(liste.get(i).getEmail().equals(email) && liste.get(i).getPassword().equals(password)) return 1;
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
