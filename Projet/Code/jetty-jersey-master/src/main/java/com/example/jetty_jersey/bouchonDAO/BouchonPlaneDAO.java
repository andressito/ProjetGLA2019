package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.classes.Plane;
import com.example.jetty_jersey.dao.PlaneDAO;

import java.util.ArrayList;
import java.util.List;

public class BouchonPlaneDAO implements PlaneDAO {


    List<Plane> liste = new ArrayList<Plane>();
    public void remplirBDPlane(){
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
    }



    public boolean createPlane(Plane plane) {
        remplirBDPlane();
        for (int i=0; i<liste.size(); i++){
            if (liste.get(i).getAtcNumber()==plane.getAtcNumber()){
                return false;
            }
        }
        liste.add(plane);
        return true;
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
        remplirBDPlane();
        return liste;
    }

    public Plane getPlaneDetails(String atcNumber) {
        return null;
    }
}
