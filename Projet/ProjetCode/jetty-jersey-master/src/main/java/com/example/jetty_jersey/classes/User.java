package com.example.jetty_jersey.classes;

public class User {
    public String idUser;
    public String lastName;
    public String firstName;
    public String email;
    public String gsm;

    public User(String idUser, String firstName, String lastName, String email, String gsm ){
        this.idUser=idUser;
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.gsm=gsm;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }
}
