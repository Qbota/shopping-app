package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Group {


    //Group props
    @Id
    public String id;
    public String name;

    //Children
    public List<User> members;
    public List<Product> demandedProducts;

    public Group() {
    }

    public Group(String name) {
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
}
