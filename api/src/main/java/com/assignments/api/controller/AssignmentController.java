package com.assignments.api.controller;

import com.assignments.api.model.Assignment;
import com.assignments.api.service.assignment.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/group/{groupId}/assignment")
    public ResponseEntity<Object> getAssignmentListForGroup(@PathVariable String groupId){
        try{
            return new ResponseEntity<>(assignmentService.getAssignmentListForGroup(groupId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/group/{groupId}/assignment/all")
    public ResponseEntity<Object> getAssignmentListForGroupAndItsMembers(@PathVariable String groupId){
        try{
            return new ResponseEntity<>(assignmentService.getAssignmentForGroupAndItsMembers(groupId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}/assignment")
    public ResponseEntity<Object> getAssignmentListForUser(@PathVariable String userId){
        try{
            return new ResponseEntity<>(assignmentService.getAssignmentListForUser(userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/group/{groupId}/assignment")
    public ResponseEntity<Object> addAssignmentForGroup(@RequestBody Assignment assignment, @PathVariable String groupId){
        try{
            assignmentService.createAssignmentForGroup(assignment, groupId);
            return new ResponseEntity<>("Assignment created", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/{userId}/assignment")
    public ResponseEntity<Object> addAssignmentForUser(@RequestBody Assignment assignment, @PathVariable String userId){
        try{
            assignmentService.createAssignmentForUser(assignment, userId);
            return new ResponseEntity<>("Assignment created", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/assignment/{assignmentId}/user/{userId}")
    public ResponseEntity<Object> attachAssignmentToUser(@PathVariable String userId, @PathVariable String assignmentId){
        try{
            return new ResponseEntity<>(assignmentService.changeAssigneeToUser(assignmentId, userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/assignment/{assignmentId}/group/{groupId}")
    public ResponseEntity<Object> attachAssignmentToGroup(@PathVariable String groupId, @PathVariable String assignmentId){
        try{
            return new ResponseEntity<>(assignmentService.changeAssigneeToGroup(assignmentId, groupId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/assignment")
    public ResponseEntity<Object> editAssignment(@RequestBody Assignment assignment){
        try{
            return new ResponseEntity<>(assignmentService.editAssignment(assignment), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
