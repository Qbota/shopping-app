package com.assignments.api.service.group;

import com.assignments.api.model.Group;
import com.assignments.api.model.User;

import java.util.List;
import java.util.Map;

public interface GroupManagementService {
    Group createGroup(Group group) throws Exception;
    Group getGroup(String id) throws Exception;
    Group addUserToGroup(String userId, String groupCode) throws Exception;
    void setStateOfGroup(boolean state, String id) throws Exception;
    List<Group> getGroupList();
    List<User> getUserListForGroup(String id) throws Exception;
    List<Map<String, Object>> getUserListWithAssignments(String id) throws Exception;

}
