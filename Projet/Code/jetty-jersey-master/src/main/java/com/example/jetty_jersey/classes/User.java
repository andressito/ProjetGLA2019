package com.example.jetty_jersey.classes;

import java.util.Date;

public class User {
    public String userId;
    public String lastName;
    public String firstName;
    public String email;
    public String gsm;
    public String birthDate;
    public String password;
    public String typeUser;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, String birthDate, String gsm,String typeUser ){
        this.userId=null;
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.gsm=gsm;
        this.birthDate=birthDate;
        this.password=password;
        this.typeUser = typeUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public void toStringUser(){
        System.out.println("id: "+this.userId+
                " firstName: "+this.firstName+
                " lastName: "+this.lastName+
                " email: "+this.email+
                " password: "+this.password+
                " birthdate: "+this.birthDate+
                " gsm: "+this.gsm+
                " typeUser: "+this.typeUser);
    }
}
