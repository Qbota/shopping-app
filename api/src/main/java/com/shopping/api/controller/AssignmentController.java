package com.shopping.api.controller;

import com.shopping.api.model.Assignment;
import com.shopping.api.model.Group;
import com.shopping.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assignement")
public class AssignmentController {

    public ResponseEntity<List<Assignment>> getAssignmentListForGroup(Group group){

    }

    public ResponseEntity<List<Assignment>> getAssignmentListForUser(User user){

    }

    public ResponseEntity<Assignment> addAssignmentForGroup(Assignment assignment){

    }

    public ResponseEntity<Assignment> addAssignmentForUser(Assignment assignment){

    }

    public ResponseEntity<Assignment> attachAssignmentToUser(Assignment assignment){

    }

    public ResponseEntity<Assignment> updateAssignment(Assignment assignment){

    }
}
