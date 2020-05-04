package com.shopping.api.controller;

import com.shopping.api.model.Assignment;
import com.shopping.api.model.Group;
import com.shopping.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    public ResponseEntity<Group> createGroup(Group group){

    }

    public ResponseEntity<Group> getGroup(String id){

    }

    public ResponseEntity<User> assignUserByCode(String code){

    }

    public ResponseEntity<Group> disableUser(Group group){

    }

    public ResponseEntity<Group> enableUser(Group group){

    }

}
