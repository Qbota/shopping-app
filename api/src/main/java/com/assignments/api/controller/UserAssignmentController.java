package com.assignments.api.controller;

import com.assignments.api.model.Assignment;
import com.assignments.api.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/user/{userId}/assignment")
    public ResponseEntity<Object> getAssignmentListForUser(@PathVariable String userId) {
        return new ResponseEntity<>(assignmentService.getAssignmentListForUser(userId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/assignment")
    public ResponseEntity<Object> addAssignmentForUser(@RequestBody Assignment assignment, @PathVariable String userId) {
        assignmentService.createAssignmentForUser(assignment, userId);
        return new ResponseEntity<>("Assignment created", HttpStatus.OK);
    }

}
