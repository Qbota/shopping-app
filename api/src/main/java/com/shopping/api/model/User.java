package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class User{

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

    public void addProductToShoppingList(Product product) {
        product.setId(Integer.toString(this.shoppingList.size()));
        this.shoppingList.add(product);
    }

    public void setProductAsBought(String id) { this.shoppingList = this.shoppingList.stream().map(product -> {
        Product copy = new Product(product);
        if(copy.getId().equals(id))
            copy.setBought(true);
        return copy;
    }).collect(Collectors.toList());}

    public Product getProduct(String id){
        return this.shoppingList.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(new Product());
    }
}

