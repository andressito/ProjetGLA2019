package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Plane;

import java.util.List;

public interface PlaneDAO {
    boolean addPlane( Plane plane );
    boolean updatePlane(String planeId);
    boolean deletePlane(String atcNumber);
    List<Plane> search(String atcNumber);
    List<Plane> getListePlane();
    Plane getPlaneDetails(String atcNumber);
}
