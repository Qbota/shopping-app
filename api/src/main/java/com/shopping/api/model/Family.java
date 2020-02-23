package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Family {


    //Family props
    @Id
    private String id;
    private String name;

    //Children
    private List<User> members =  new ArrayList<>();
    private List<Product> demandedProducts = new ArrayList<>();

    public Family() {
    }

    public Family(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Product> getDemandedProducts() {
        return demandedProducts;
    }

    public void setDemandedProducts(List<Product> demandedProducts) {
        this.demandedProducts = demandedProducts;
    }

    public void addMemeber(User user){ this.members.add(user); }

    public User getMember(String login){
       return this.members.stream().filter(x -> x.getLogin().equals(login)).findFirst().orElse(new User());
    }
}
