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
    /*public void remplirBDPlane(){
        Plane p1 = new Plane("ACT123",5);
        Plane p2 = new Plane("ACT124",3);
        Plane p3 = new Plane("ACT125",8);
        Plane p4 = new Plane("ACT126",7);
        Plane p5 = new Plane("ACT127",2);
        liste.add(p1);
        liste.add(p2);
        liste.add(p3);
        liste.add(p4);
        liste.add(p5);
    }*/



    public boolean createPlane(Plane plane) {
        try {
            JettyMain.c.indexDB(plane);
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
        ArrayList<Plane> planes = null;
        try {
            planes = JettyMain.c.allPlane();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return planes;
    }

    public Plane getPlaneDetails(String atcNumber) {
        return null;
    }
}
