package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Product {

    //Product props
    @Id
    private String id;
    private String name;
    private Date dateToBuy;
    private Boolean isBought;

    public Product() {

    }

    public Product(String id, String name, Date dateToBuy, Boolean isBought) {
        this.id = id;
        this.name = name;
        this.dateToBuy = dateToBuy;
        this.isBought = isBought;
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

    public Date getDateToBuy() {
        return dateToBuy;
    }

    public void setDateToBuy(Date dateToBuy) {
        this.dateToBuy = dateToBuy;
    }

    public Boolean getBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }

}
