package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.classes.Aerodrom;
import com.example.jetty_jersey.dao.AerodromDAO;

import java.util.ArrayList;

public class BouchonAerodromDAO implements AerodromDAO {
    public boolean createAerodrome(Aerodrom aero) {
        return false;
    }

    public ArrayList<Aerodrom> searchFlight(String aero) {
        return null;
    }

    public ArrayList<Aerodrom> getListeAerodrome() {
        return null;
    }

    public Aerodrom getAerodromeDetails(String aerodromeId) {
        return null;
    }
}
