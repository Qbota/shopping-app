package com.shopping.api.validator;

import com.shopping.api.model.User;

class UserValidator extends AbstractValidator<User> implements ModelValidator{

    public UserValidator(User object) {
        super(object);
    }

    public boolean validate(){
        if(isObjectNotValid())
            return false;
        else if(isStringAttributeNotValid(object.getLogin()))
            return false;
        else if(isStringAttributeNotValid(object.getPassword()))
            return false;
        return true;
    }
}
