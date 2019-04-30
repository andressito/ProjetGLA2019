package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Aerodrom;

import java.util.ArrayList;

public interface AerodromDAO {
    boolean createAerodrome( Aerodrom aero );
    ArrayList<Aerodrom> searchFlight(String aero);
    ArrayList<Aerodrom> getListeAerodrom();
    Aerodrom getAerodromDetails(String aerodromeId);
}
