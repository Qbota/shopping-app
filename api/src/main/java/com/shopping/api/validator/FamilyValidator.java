package com.shopping.api.validator;

import com.shopping.api.model.Family;
import com.shopping.api.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

class FamilyValidator extends AbstractValidator<Family> implements ModelValidator{

    public FamilyValidator(Family object) {
        super(object);
    }

    @Override
    public boolean validate() {
        if(isObjectNotValid())
            return false;
        else if(isStringAttributeNotValid(object.getName()))
            return false;
        else if(isStringAttributeNotValid(object.getPassword()))
            return false;
        return true;
    }
}
