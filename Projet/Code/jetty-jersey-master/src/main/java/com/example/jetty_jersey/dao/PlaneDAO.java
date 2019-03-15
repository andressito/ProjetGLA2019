package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Plane;

import java.util.List;

public interface PlaneDAO {
    boolean createPlane( Plane plane );
    boolean updatePlane(String atcNumber);
    boolean deletePlane(String atcNumber);
    List<Plane> search(String atcNumber);
    List<Plane> getAllPlane();
    Plane getPlaneDetails(String atcNumber);
}
