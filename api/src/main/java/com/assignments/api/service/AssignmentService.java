package com.assignments.api.service;

import com.assignments.api.error.exception.DocumentInvalidException;
import com.assignments.api.error.exception.DocumentNotFoundException;
import com.assignments.api.model.Assignment;
import com.assignments.api.model.Group;
import com.assignments.api.model.State;
import com.assignments.api.model.User;
import com.assignments.api.repository.AssignmentRepository;
import com.assignments.api.repository.GroupRepository;
import com.assignments.api.repository.UserRepository;
import com.assignments.api.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;

    public void createAssignmentForGroup(Assignment assignment, String groupId) {
        Validators.addAssignmentValidator(assignment);
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(DocumentNotFoundException::new);
        assignment.setAssignee(groupId);
        assignmentRepository.save(assignment);
    }

    public void createAssignmentForUser(Assignment assignment, String userId) {
        Validators.addAssignmentValidator(assignment);
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(DocumentNotFoundException::new);
        assignment.setAssignee(userId);
        assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentListForGroup(String groupId) {
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(DocumentNotFoundException::new);
        return assignmentRepository.findByAssignee(groupId).orElseThrow(DocumentNotFoundException::new);
    }

    public List<Assignment> getAssignmentListForUser(String userId) {
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(DocumentNotFoundException::new);
        return assignmentRepository.findByAssignee(userId).orElseThrow(DocumentNotFoundException::new);
    }

    public Assignment changeAssigneeToUser(String assignmentId, String userId) {
        userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(DocumentNotFoundException::new);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(DocumentNotFoundException::new);
        assignment.setAssignee(userId);
        return assignmentRepository.save(assignment);
    }

    public Assignment changeAssigneeToGroup(String assignmentId, String groupId) {
        groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(DocumentNotFoundException::new);
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(DocumentNotFoundException::new);
        assignment.setAssignee(groupId);
        return assignmentRepository.save(assignment);
    }

    public Assignment editAssignment(Assignment assignment) {
        if (assignment == null)
            throw new DocumentInvalidException("Assignment was null");
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentForGroupAndItsMembers(String groupId) {
        Group group = groupRepository.findByIdAndIsActiveTrue(groupId).orElseThrow(DocumentNotFoundException::new);
        List<User> userList = userRepository.findByGroupId(groupId).orElseThrow(DocumentNotFoundException::new);
        List<Assignment> assignmentList = new ArrayList<>(
                replaceIdWithName(group.getName(),
                        assignmentRepository.findByAssignee(groupId).orElseThrow(DocumentNotFoundException::new))
        );
        userList.forEach(user -> assignmentList.addAll(
                replaceIdWithName(user.getLogin(), assignmentRepository.findByAssignee(user.getId()).orElseThrow(DocumentNotFoundException::new)))
        );
        return assignmentList;
    }

    private List<Assignment> replaceIdWithName(String login, List<Assignment> assignmentList) {
        List<Assignment> modified = new ArrayList<>();
        assignmentList.forEach(assignment -> {
            Assignment modifiedAssignment = new Assignment(assignment);
            modifiedAssignment.setAssignee(login);
            modified.add(modifiedAssignment);
        });
        return modified;
    }
}
