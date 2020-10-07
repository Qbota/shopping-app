package com.assignments.api.service.group;

import com.assignments.api.model.Group;
import com.assignments.api.model.User;

import java.util.List;

public interface GroupManagementService {
    Group createGroup(Group group) throws Exception;
    Group getGroup(String id) throws Exception;
    Group addUserToGroup(String userId, String groupCode) throws Exception;
    void setStateOfGroup(boolean state, String id) throws Exception;
    List<Group> getGroupList();
    List<User> getUserListForGroup(String id) throws Exception;

}
