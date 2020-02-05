package com.shopping.api.service;

import com.shopping.api.model.Group;
import com.shopping.api.model.User;
import com.shopping.api.service.exceptions.NotUserFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends AbstractService{

    public List<User> getAllUsers(){
        List<Group> groups = _repo.findAll();
        return extractUsers(groups);
    }

    public User getUser(String name){
        try{
            return tryToGetUser(name);
        }catch (NotUserFoundException exception){
            System.out.println(exception.getStackTrace());
            return new User();
        }
    }

    private User tryToGetUser(String name) throws NotUserFoundException {

    }

    private List<User> extractUsers(List<Group> groups){
        List<User> users = new ArrayList<>();
        for (Group group: groups) {
            users.addAll(group.getMembers());
        }
        return users;
    }

}
