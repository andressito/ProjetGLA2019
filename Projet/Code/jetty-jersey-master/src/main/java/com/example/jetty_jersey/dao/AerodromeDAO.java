package com.example.jetty_jersey.dao;


import java.util.ArrayList;
import java.util.List;


import com.example.jetty_jersey.classes.Aeorodrome;

public interface AerodromeDAO {
    boolean createAerodrome( Aeorodrome aero );
    ArrayList<Aeorodrome> searchFlight(String aero);
    List<Aeorodrome> getListeAerodrome();
    Aeorodrome getAerodromeDetails(String aerodromeId);
}
