package com.assignments.api.controller;

import com.assignments.api.model.User;
import com.assignments.api.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userManagementService.createUser(user), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserList() {
        return new ResponseEntity<>(userManagementService.getUserList(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody User user) {
        return new ResponseEntity<>(userManagementService.authenticate(user), HttpStatus.OK);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<Object> disableUser(@PathVariable String id) {
        return new ResponseEntity<>(userManagementService.changeStatusOfUser(false, id), HttpStatus.OK);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<Object> enableUser(@PathVariable String id) {
        return new ResponseEntity<>(userManagementService.changeStatusOfUser(true, id), HttpStatus.OK);
    }
}
