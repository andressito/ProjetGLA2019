package com.example.jetty_jersey.classes;

import java.util.Date;

public class User {
    public int idUser;
    public String lastName;
    public String firstName;
    public String email;
    public String gsm;
    public Date birthDate;
    public String password;

    public User(int idUser, String firstName, String lastName, String email, String password, Date birthDate, String gsm ){
        this.idUser=idUser;
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.gsm=gsm;
        this.birthDate=birthDate;
        this.password=password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
