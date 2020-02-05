package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class User {

    //User props
    @Id
    public String id;
    public String login;
    public String password;

    //Children
    public List<Product> shoppingList;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public List<Product> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Product> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

