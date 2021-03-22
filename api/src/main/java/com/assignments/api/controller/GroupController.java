package com.assignments.api.controller;

import com.assignments.api.model.Group;
import com.assignments.api.service.GroupManagementService;
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
    public ResponseEntity<Object> createGroup(@RequestBody Group group) {
        return new ResponseEntity<>(groupManagementService.createGroup(group), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Object> getGroupList() {
        return new ResponseEntity<>(groupManagementService.getGroupList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGroup(@PathVariable String id) {
        return new ResponseEntity<>(groupManagementService.getGroup(id), HttpStatus.OK);
    }

    @PostMapping("/{code}/user/{userId}")
    public ResponseEntity<Object> assignUserByCode(@PathVariable String userId, @PathVariable String code) {
        return new ResponseEntity<>(groupManagementService.addUserToGroup(userId, code), HttpStatus.OK);
    }

    @GetMapping("/{groupId}/user")
    public ResponseEntity<Object> getUserListForGroup(@PathVariable String groupId) {
        return new ResponseEntity<>(groupManagementService.getUserListWithAssignments(groupId), HttpStatus.OK);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<Object> disableGroup(@PathVariable String id) {
        groupManagementService.setStateOfGroup(false, id);
        return new ResponseEntity<>("Group disabled", HttpStatus.OK);
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<Object> enableGroup(@PathVariable String id) {
        groupManagementService.setStateOfGroup(true, id);
        return new ResponseEntity<>("Group enabled", HttpStatus.OK);
    }

    @GetMapping("/{id}/ranking")
    public ResponseEntity<Object> getGroupRanking(@PathVariable String id) throws Exception {
        return new ResponseEntity<>(groupManagementService.getRanking(id), HttpStatus.OK);
    }

}
