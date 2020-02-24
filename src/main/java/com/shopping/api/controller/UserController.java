package com.shopping.api.controller;

import com.shopping.api.model.User;
import com.shopping.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login){
        try{
            return new ResponseEntity<>(userService.getUser(login), HttpStatus.FOUND);
        }catch (ChangeSetPersister.NotFoundException e){
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> getListOfUsers(){
        return new ResponseEntity<>(userService.getListOfUsers(), HttpStatus.FOUND);
    }

}
