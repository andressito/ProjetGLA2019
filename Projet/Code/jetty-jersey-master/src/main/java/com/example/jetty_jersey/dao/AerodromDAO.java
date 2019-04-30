package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Aerodrom;

import java.util.ArrayList;

public interface AerodromDAO {
    ArrayList<Aerodrom> searchFlight(String aero);
    ArrayList<Aerodrom> getListAerodrom();
    Aerodrom getAerodromDetails(String aerodromeId);
}
