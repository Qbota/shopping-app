package com.assignments.api.service.user;

import com.assignments.api.model.User;

import java.util.List;

public interface UserManagementService {
    User createUser(User user) throws Exception;
    List<User> getUserList();
    User changeStatusOfUser(boolean status, String id) throws Exception;
    User authenticate(User user) throws Exception;
}
