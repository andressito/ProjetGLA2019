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

    public ArrayList<Aerodrom> getListeAerodrom() {
        return null;
    }

    public Aerodrom getAerodromDetails(String aerodromeId) {
        return null;
    }
}
