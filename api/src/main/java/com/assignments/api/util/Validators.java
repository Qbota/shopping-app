package com.assignments.api.util;

import com.assignments.api.error.exception.DocumentInvalidException;
import com.assignments.api.model.Assignment;
import com.assignments.api.model.Group;
import com.assignments.api.model.Product;
import com.assignments.api.model.User;

import java.security.InvalidParameterException;

public class Validators {

    public static void insertUserValidator(User user) {
        if (user == null)
            throw new DocumentInvalidException("User was null");
        if (user.getLogin().isEmpty())
            throw new DocumentInvalidException("Login was empty");
        if (user.getPassword().isEmpty())
            throw new DocumentInvalidException("Password was empty");
    }

    public static void authenticateUserValidator(User user) {
        if (user == null)
            throw new DocumentInvalidException("User was null");
        if (user.getLogin().isEmpty())
            throw new DocumentInvalidException("Login was empty");
        if (user.getPassword().isEmpty())
            throw new DocumentInvalidException("Password was empty");
    }

    public static void insertGroupValidator(Group group) {
        if (group == null)
            throw new DocumentInvalidException("Group was null");
        if (group.getName().isEmpty())
            throw new DocumentInvalidException("Group name was empty");
    }

    public static void addAssignmentValidator(Assignment assignment) {
        if (assignment == null)
            throw new DocumentInvalidException("Assignment was null");
        if (assignment.getName().isEmpty())
            throw new DocumentInvalidException("Assignment name was null");
        if (assignment.getDescription().isEmpty())
            throw new DocumentInvalidException("Assignment description was null");
        if (assignment.getType().isEmpty())
            throw new DocumentInvalidException("Assignment type was null");
        if (assignment.getAddedBy().isEmpty())
            throw new DocumentInvalidException("Assignment addedBy was null");
    }

    public static void createShoppingProductValidator(Product product) {
        if (product == null)
            throw new DocumentInvalidException("Product was null");
        if( product.getType().isEmpty())
            throw new DocumentInvalidException("Type was empty");
        if (product.getName().isEmpty())
            throw new DocumentInvalidException("Name was empty");
    }

}
