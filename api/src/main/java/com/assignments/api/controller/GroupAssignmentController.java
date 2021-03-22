package com.assignments.api.controller;

import com.assignments.api.model.Assignment;
import com.assignments.api.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GroupAssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/group/{groupId}/assignment")
    public ResponseEntity<Object> getAssignmentListForGroup(@PathVariable String groupId) {
        return new ResponseEntity<>(assignmentService.getAssignmentListForGroup(groupId), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}/assignment/all")
    public ResponseEntity<Object> getAssignmentListForGroupAndItsMembers(@PathVariable String groupId) {
        return new ResponseEntity<>(assignmentService.getAssignmentForGroupAndItsMembers(groupId), HttpStatus.OK);
    }

    @PostMapping("/group/{groupId}/assignment")
    public ResponseEntity<Object> addAssignmentForGroup(@RequestBody Assignment assignment, @PathVariable String groupId) {
        assignmentService.createAssignmentForGroup(assignment, groupId);
        return new ResponseEntity<>("Assignment created", HttpStatus.OK);
    }

}
