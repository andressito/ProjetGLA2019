package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Plane;
import com.example.jetty_jersey.dao.PlaneDAO;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BouchonPlaneDAO implements PlaneDAO {

    static List<Plane>  liste= new ArrayList<Plane>();
    public boolean createPlane(Plane plane) {
        try {
            JettyMain.c.indexDB(plane,null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updatePlane(String atcNumber) {
        return false;
    }

    public boolean deletePlane(String atcNumber) {
        for (int i=0; i<liste.size(); i++){
            if (liste.get(i).getAtcNumber().equals(atcNumber)){
                liste.remove(i);
                System.out.println("plane deleted");
                return true;
            }
        }
        return false;
    }

    public List<Plane> search(String atcNumber) {
        return null;
    }

    public List<Plane> getAllPlane() {
        try {
            return JettyMain.c.allPlane();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Plane getPlaneDetails(String atcNumber) {
        return null;
    }
}
