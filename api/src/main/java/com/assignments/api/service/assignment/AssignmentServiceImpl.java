package com.assignments.api.service.assignment;

import com.assignments.api.model.Assignment;
import com.assignments.api.model.Group;
import com.assignments.api.model.User;
import com.assignments.api.repository.AssignmentRepository;
import com.assignments.api.repository.GroupRepository;
import com.assignments.api.repository.UserRepository;
import com.assignments.api.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    public void createAssignmentForGroup(Assignment assignment, String groupId) throws Exception {
        Validators.addAssignmentValidator(assignment);
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(Exception::new);
        assignment.setAssignee(groupId);
        assignmentRepository.save(assignment);
    }

    public void createAssignmentForUser(Assignment assignment, String userId) throws Exception {
        Validators.addAssignmentValidator(assignment);
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(Exception::new);
        assignment.setAssignee(userId);
        assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentListForGroup(String groupId) throws Exception {
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(Exception::new);
        return assignmentRepository.findByAssignee(groupId).orElseThrow(Exception::new);
    }

    public List<Assignment> getAssignmentListForUser(String userId) throws Exception {
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(Exception::new);
        return assignmentRepository.findByAssignee(userId).orElseThrow(Exception::new);
    }

    public Assignment changeAssigneeToUser(String assignmentId, String userId) throws Exception {
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(Exception::new);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(Exception::new);
        assignment.setAssignee(userId);
        return assignmentRepository.save(assignment);
    }

    public Assignment changeAssigneeToGroup(String assignmentId, String groupId) throws Exception {
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(Exception::new);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(Exception::new);
        assignment.setAssignee(groupId);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment editAssignment(Assignment assignment) throws Exception {
        if(assignment == null)
            throw new Exception("Assignment was null");
        return assignmentRepository.save(assignment);
    }
}
