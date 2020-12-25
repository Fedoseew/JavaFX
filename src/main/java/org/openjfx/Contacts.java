package org.openjfx;

import javafx.beans.property.SimpleStringProperty;

public class Contacts {
    private String name;
    private String phone;
    private String email;
    private String user;

    public Contacts(){}

    public Contacts(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.user = PrimaryController.activateUser;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
