package com.assignments.api.service.assignment;

import com.assignments.api.model.Assignment;

import java.util.List;

public interface AssignmentService {

    void createAssignmentForGroup(Assignment assignment, String groupId) throws Exception;
    void createAssignmentForUser(Assignment assignment, String userId) throws Exception;
    List<Assignment> getAssignmentListForGroup(String groupId) throws Exception;
    List<Assignment> getAssignmentListForUser(String userId) throws Exception;
    Assignment changeAssigneeToUser(String assignmentId, String userId) throws Exception;
    Assignment changeAssigneeToGroup(String assignmentId, String groupId) throws Exception;
    Assignment editAssignment(Assignment assignment) throws Exception;
    List<Assignment> getAssignmentForGroupAndItsMembers(String groupId) throws Exception;

}
