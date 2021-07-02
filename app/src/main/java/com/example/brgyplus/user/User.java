package com.example.brgyplus.user;

public class User {
    public String firstname, lastname, email, userType;

    public User(){

    }

    public User(String firstname, String lastname, String email, String userType){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
    }

}
