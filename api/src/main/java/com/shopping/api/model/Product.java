package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Product {

    //Product props
    @Id
    public String id;
    public String name;
    public Date dateToBuy;
    public Boolean isBought;

}
