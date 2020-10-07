package com.assignments.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Assignment {

    @Id
    private String id;
    private String name;
    private String description;
    private String type;
    private Date begin;
    private Date end;
    private String addedBy;
    private String state;
    private String assignee;

    public Assignment(){

    }

    public Assignment(Assignment assignment) {
        this.id = assignment.getId();
        this.name = assignment.getName();
        this.description = assignment.getDescription();
        this.type = assignment.getType();
        this.begin = assignment.getBegin();
        this.end = assignment.getEnd();
        this.addedBy = assignment.getAddedBy();
        this.state = assignment.getState();
        this.assignee = assignment.getAssignee();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
