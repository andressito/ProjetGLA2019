package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Aeorodrome;
import com.example.jetty_jersey.dao.AerodromeDAO;


import java.util.ArrayList;
import java.util.List;

public class BouchonAerodromeDAO implements AerodromeDAO {
    static ArrayList<Aeorodrome>  liste= new ArrayList<Aeorodrome>();
    public boolean createAerodrome(Aeorodrome aero) {
		// TODO Auto-generated method stub
		return false;
	}
	public ArrayList<Aeorodrome> searchFlight(String aero) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Aeorodrome> getListeAerodrome() {
		try {
            return liste = JettyMain.c.allAerodrome();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	public Aeorodrome getAerodromeDetails(String aerodromeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
