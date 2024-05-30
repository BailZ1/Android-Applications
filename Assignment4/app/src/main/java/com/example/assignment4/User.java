// Assignment 4
// User.java
// [Your Full Name]

package com.example.assignment4;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String age;
    private String country;
    private String dob;

    public User(String name, String email, String age, String country, String dob) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.country = country;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getDob() {
        return dob;
    }
}
