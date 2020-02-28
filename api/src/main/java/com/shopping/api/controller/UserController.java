package com.shopping.api.controller;

import com.shopping.api.model.Family;
import com.shopping.api.model.User;
import com.shopping.api.service.FamilyService;
import com.shopping.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FamilyService familyService;

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

    @GetMapping("/family/{name}/user")
    public ResponseEntity<List<User>> getMembersOfFamily(@PathVariable String name){
        try {
            return new ResponseEntity<>(familyService.getFamilyByName(name).getMembers(), HttpStatus.FOUND);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new Family().getMembers(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/family/{name}/user")
    public ResponseEntity<User> addUserToFamily(@PathVariable String name, @RequestBody User user){
        try {
            return new ResponseEntity<>(userService.addNewUserToFamily(user, name), HttpStatus.CREATED);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/family/{name}/user")
    public ResponseEntity<User> removeUserFromFamily(@PathVariable String name, @RequestBody User user){
        try{
            return new ResponseEntity<>(userService.removeUserFromFamily(user, name), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(new User(), HttpStatus.BAD_REQUEST);
        }
    }

}
