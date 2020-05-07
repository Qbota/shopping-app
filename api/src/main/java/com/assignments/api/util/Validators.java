package com.assignments.api.util;

import com.assignments.api.model.Assignment;
import com.assignments.api.model.Group;
import com.assignments.api.util.exception.ObjectIsNullException;
import com.assignments.api.model.User;

import java.security.InvalidParameterException;

public class Validators {

    public static void insertUserValidator(User user) throws Exception{
        if(user == null)
            throw new ObjectIsNullException("User was null");
        if(user.getLogin().isEmpty())
            throw new InvalidParameterException("Login was empty");
        if(user.getPassword().isEmpty())
            throw new InvalidParameterException("Password was empty");
    }

    public static void authenticateUserValidator(User user) throws Exception{
        if(user == null)
            throw new ObjectIsNullException("User was null");
        if(user.getLogin().isEmpty())
            throw new InvalidParameterException("Login was empty");
        if(user.getPassword().isEmpty())
            throw new InvalidParameterException("Password was empty");
    }

    public static void insertGroupValidator(Group group) throws Exception{
        if(group == null)
            throw new ObjectIsNullException("Group was null");
        if(group.getName().isEmpty())
            throw new InvalidParameterException("Group name was empty");
    }

    public static void addAssignmentValidator(Assignment assignment) throws Exception{
        if(assignment == null)
            throw new ObjectIsNullException("Assignment was null");
        if(assignment.getName().isEmpty())
            throw new InvalidParameterException("Assignment name was null");
        if(assignment.getDescription().isEmpty())
            throw new InvalidParameterException("Assignment description was null");
        if(assignment.getType().isEmpty())
            throw new InvalidParameterException("Assignment type was null");
        if(assignment.getAddedBy().isEmpty())
            throw new InvalidParameterException("Assignment addedBy was null");
    }
}
