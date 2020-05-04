package com.shopping.api.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class User {

    @Id
    private String id;
    private String login;
    private String password;
    private List<Assignment> assignmentList = new ArrayList<>();
    private boolean isActive;

    public User() {
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

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public void addAssignement(Assignment assignment){
        this.assignmentList.add(assignment);
    }

    public void removeAssignement(Assignment assignment){
        this.assignmentList.remove(assignment);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
