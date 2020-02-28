package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Family {


    //Family props
    @Id
    private String id;
    private String name;
    private String password;

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
       return this.members.stream().filter(x -> x.getLogin().equals(login)).findFirst().orElse( null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Product getDemandedProduct(String id){
        return this.demandedProducts.stream().filter(x -> x.getId().equals(id)).findFirst().orElse( null );
    }

    public void addDemandedProduct(Product product) {
        product.setId(Integer.toString(this.demandedProducts.size()));
        this.demandedProducts.add(product);
    }

    public void setProductAsBought(String id) { this.demandedProducts = this.demandedProducts.stream().map(product -> {
        Product copy = new Product(product);
        if(copy.getId().equals(id))
            copy.setBought(true);
        return copy;
    }).collect(Collectors.toList());}

    public void removeMember(User user){
        this.members.remove(user);
    }

    public void removeProduct(Product product) {this.demandedProducts.remove(product);}

    public void modifyUser(User user) {
        Collections.replaceAll(this.members, getMember(user.getLogin()), user);
    }
}
