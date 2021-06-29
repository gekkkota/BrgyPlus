package com.example.brgyplus;

public class User {
    public String firstname, lastname, email, userType, token;

    public User(){

    }

    public User(String firstname, String lastname, String email, String userType){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
    }

    public User(String email, String token){
        this.email = email;
        this.token = token;
    }
}
