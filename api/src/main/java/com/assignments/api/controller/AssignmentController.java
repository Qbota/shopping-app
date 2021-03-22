package com.assignments.api.controller;

import com.assignments.api.model.Assignment;
import com.assignments.api.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PutMapping("/assignment/{assignmentId}/group/{groupId}")
    public ResponseEntity<Object> attachAssignmentToGroup(@PathVariable String groupId, @PathVariable String assignmentId) {
        return new ResponseEntity<>(assignmentService.changeAssigneeToGroup(assignmentId, groupId), HttpStatus.OK);
    }

    @PutMapping("/assignment/{assignmentId}/user/{userId}")
    public ResponseEntity<Object> attachAssignmentToUser(@PathVariable String userId, @PathVariable String assignmentId) {
        return new ResponseEntity<>(assignmentService.changeAssigneeToUser(assignmentId, userId), HttpStatus.OK);
    }

    @PutMapping("/assignment")
    public ResponseEntity<Object> editAssignment(@RequestBody Assignment assignment) {

        return new ResponseEntity<>(assignmentService.editAssignment(assignment), HttpStatus.OK);
    }
}
