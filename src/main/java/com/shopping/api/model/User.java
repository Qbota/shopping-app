package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class User {

    //User props
    private String login;
    private String password;

    //Children
    private List<Product> shoppingList = new ArrayList<>();

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

    public void addProductToShoppingList(Product product) { this.shoppingList.add(product);}
}

