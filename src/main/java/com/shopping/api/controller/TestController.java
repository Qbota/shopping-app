package com.shopping.api.controller;

import com.shopping.api.model.User;
import com.shopping.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<User> getTestUser(){
        try {
            return new ResponseEntity<>(userService.addNewUserToFamily(new User("test","test"),"test"),HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
        }
    }


}
