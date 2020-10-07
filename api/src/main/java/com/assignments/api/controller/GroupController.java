package com.assignments.api.controller;

import com.assignments.api.model.Group;
import com.assignments.api.service.group.GroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupManagementService groupManagementService;

    @PostMapping("")
    public ResponseEntity<Object> createGroup(@RequestBody Group group){
        try{
            return new ResponseEntity<>(groupManagementService.createGroup(group), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getGroupList(){
        return new ResponseEntity<>(groupManagementService.getGroupList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGroup(@PathVariable String id){
        try{
            return new ResponseEntity<>(groupManagementService.getGroup(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{code}/user/{userId}")
    public ResponseEntity<Object> assignUserByCode(@PathVariable String userId, @PathVariable String code){
        try{
            return new ResponseEntity<>(groupManagementService.addUserToGroup(userId, code), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{groupId}/user")
    public ResponseEntity<Object> getUserListForGroup(@PathVariable String groupId){
        try{
            return new ResponseEntity<>(groupManagementService.getUserListWithAssignments(groupId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<Object> disableGroup(@PathVariable String id){
        try{
            groupManagementService.setStateOfGroup(false, id);
            return new ResponseEntity<>("Group disabled", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<Object> enableGroup(@PathVariable String id){
        try{
            groupManagementService.setStateOfGroup(true, id);
            return new ResponseEntity<>("Group enabled", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
