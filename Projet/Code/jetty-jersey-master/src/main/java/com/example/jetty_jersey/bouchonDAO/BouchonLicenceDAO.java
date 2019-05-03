package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Licence;
import com.example.jetty_jersey.dao.LicenceDAO;

import java.util.ArrayList;
import java.util.List;

public class BouchonLicenceDAO implements LicenceDAO {
    public boolean addLicence(Licence licence) {
        try {
            return JettyMain.c.indexDB(licence,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateLicence(String licenceId) {

        //à implémenter
        return false;
    }

    public boolean deleteLicence(String licenceId) {
        //à implémenter
        return false;
    }

    public Licence getLicenceByUserId(String userId) {
        try {
            ArrayList<Licence> liste = JettyMain.c.allLicence();
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
            ArrayList<Licence> liste = JettyMain.c.allLicence();
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
            return JettyMain.c.allLicence();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
