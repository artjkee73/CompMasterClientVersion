package com.androiddev.artemqa.compmasterclientversion.models;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Client extends RealmObject {
    @Required
    private String login;
    @Required
    private String password;
    private String email;
    private String name;
    private String address;

    public Client() {

    }

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}