package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Licence;

import java.util.List;

public interface LicenceDAO {
    boolean addLicence(Licence licence);
    boolean updateLicence( String licenceId);
    boolean deleteLicence(String licenceId);
    Licence getLicence(String userId);
    List<Licence> getAllLicence();

}
