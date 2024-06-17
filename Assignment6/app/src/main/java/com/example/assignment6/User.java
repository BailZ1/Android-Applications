package com.example.assignment6;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private int age;
    private String country;
    private String dateOfBirth;

    public User(String name, String email, int age, String country, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
