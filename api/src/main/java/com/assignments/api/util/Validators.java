package com.assignments.api.util;

import com.assignments.api.util.exception.ObjectIsNullException;
import com.assignments.api.model.User;

import java.security.InvalidParameterException;

public class Validators {

    public static void userCanBeInsertedValidator(User user) throws Exception{
        if(user == null)
            throw new ObjectIsNullException("User was null");
        if(user.getLogin().isEmpty())
            throw new InvalidParameterException("Login was empty");
        if(user.getPassword().isEmpty())
            throw new InvalidParameterException("Password was empty");
    }

    public static void userCanBeAuthenticated(User user) throws Exception{
        if(user == null)
            throw new ObjectIsNullException("User was null");
        if(user.getLogin().isEmpty())
            throw new InvalidParameterException("Login was empty");
        if(user.getPassword().isEmpty())
            throw new InvalidParameterException("Password was empty");
    }
}
