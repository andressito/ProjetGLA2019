package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.LicenceDAO;

import java.util.ArrayList;
import java.util.List;

public class BouchonLicenceDAO implements LicenceDAO {
    User user ;
    static ArrayList<Licence> liste= new ArrayList<Licence>();
    public boolean addLicence(Licence licence) {
        System.out.println(""+licence.getUserId());
        try {
            JettyMain.c.indexDB(licence,null);
            //JettyMain.c.updateUserInIndexDonko(licence.getUserId(),"pilot",user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateLicence(String licenceId) {
        return false;
    }

    public boolean deleteLicence(String licenceId) {
        return false;
    }

    public Licence getLicenceByUserId(String userId) {
        try {
            liste = JettyMain.c.allLicence();
            for(int i=0; i<liste.size(); i++){
                if(liste.get(i).getUserId().equals(userId))return liste.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Licence getLicenceById(String licenceId) {
        try {
            liste = JettyMain.c.allLicence();
            for(int i=0; i<liste.size(); i++){
                if(liste.get(i).getLicenceId().equals(licenceId))return liste.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Licence> getAllLicence() {
        try {
            liste = JettyMain.c.allLicence();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
}
