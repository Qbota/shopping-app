package com.shopping.api.validator;

import com.shopping.api.model.Family;

class FamilyValidator extends AbstractValidator<Family> implements ModelValidator{

    public FamilyValidator(Family object) {
        super(object);
    }

    @Override
    public boolean validate() {
        if(isObjectNotValid())
            return false;
        if(isStringAttributeNotValid(object.getName()))
            return false;
        if(isStringAttributeNotValid(object.getPassword()))
            return false;
        return true;
    }
}
