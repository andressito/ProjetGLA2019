package com.example.jetty_jersey.classes;

import java.util.Date;

public class Licence {
    public String idLicence;
    public String userId;
    public Date dateValidite;

    public Licence(String idLicence, String userId, Date dateValidite){
        this.idLicence=idLicence;
        this.userId=userId;
        this.dateValidite=dateValidite;
    }

    public String getIdLicence() {
        return idLicence;
    }

    public String getUserId() {
        return userId;
    }

    public Date getDateValidite() {
        return dateValidite;
    }

    public void setIdLicence(String idLicence) {
        this.idLicence = idLicence;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDateValidite(Date dateValidite) {
        this.dateValidite = dateValidite;
    }
}
