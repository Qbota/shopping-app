package com.shopping.api.model;

import java.util.Date;

public class Assignment {

    private String name;
    private String description;
    private String type;
    private Date begin;
    private Date end;
    private String addedBy;
    private String state;

    public Assignment(String name, String description, String type, Date begin, Date end, String addedBy) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.begin = begin;
        this.end = end;
        this.addedBy = addedBy;
    }
    public Assignment(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
