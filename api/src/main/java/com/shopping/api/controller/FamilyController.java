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

import java.util.List;

@RestController
@RequestMapping("/api")
public class FamilyController {

    @Autowired
    private FamilyService familyService;
    @Autowired
    private UserService userService;

    @GetMapping("/family/{name}")
    public ResponseEntity<Family> getFamilyByName(@PathVariable String name){
        try{
            return new ResponseEntity<>(familyService.getFamilyByName(name), HttpStatus.FOUND);
        }catch(IllegalArgumentException e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }catch (ChangeSetPersister.NotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/family")
    public ResponseEntity<List<Family>> getAllFamilies(){
        return new ResponseEntity<>(familyService.getFamilyList(), HttpStatus.FOUND);
    }

    @PostMapping("/family")
    public ResponseEntity<Family> createNewFamily(@RequestBody Family family){
        try {
            return new ResponseEntity<>(familyService.createNewFamily(family), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(family, HttpStatus.BAD_REQUEST);
        }
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
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }
}
