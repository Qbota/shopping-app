package com.shopping.api.controller;

import com.shopping.api.model.Assignment;
import com.shopping.api.model.Group;
import com.shopping.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    public ResponseEntity<User> createUser(User user){

    }

    public ResponseEntity<List<User>> getUserList(){

    }

    public ResponseEntity<User> authenticateUser(User user){

    }

    public ResponseEntity<User> disableUser(User user){

    }

    public ResponseEntity<User> enableUser(User user){

    }
}
