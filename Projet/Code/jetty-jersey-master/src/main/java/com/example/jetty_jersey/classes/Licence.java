package com.example.jetty_jersey.classes;



public class Licence {
    public String licenceId;
    public String userId;
    public String validityDate;
    public int mark;
    public int numberHoursFlight;
    public Licence(){

    }
    public Licence(String licenceId, String userId, String validityDate,int mark, int numberHoursFlight){
        this.licenceId=licenceId;
        this.userId=userId;
        this.validityDate=validityDate;
        this.mark=mark;
        this.numberHoursFlight=numberHoursFlight;
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

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getNumberHoursFlight() {
        return numberHoursFlight;
    }

    public void setNumberHoursFlight(int numberHoursFlight) {
        this.numberHoursFlight = numberHoursFlight;
    }
}
