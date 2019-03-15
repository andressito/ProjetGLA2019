package com.example.jetty_jersey.classes;

import java.util.Date;

public class Licence {
    public String licenceId;
    public String userId;
    public Date validityDate;

    public Licence(String licenceId, String userId, Date validityDate){
        this.licenceId=licenceId;
        this.userId=userId;
        this.validityDate=validityDate;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }
}
