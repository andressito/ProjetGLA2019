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
                System.out.println(liste.get(i).getEmail());
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
