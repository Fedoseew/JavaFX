package org.openjfx;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private String firstname;
    private String lastname;
    private String gender;
    private String username;
    private String password;

    public User(String firstname, String lastname, String gender, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.username = username;
        this.password = password;
    }

    public User() {}

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
