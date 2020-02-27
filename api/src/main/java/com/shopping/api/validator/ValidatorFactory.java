package com.shopping.api.validator;

import com.shopping.api.model.Family;
import com.shopping.api.model.Product;
import com.shopping.api.model.User;

public class ValidatorFactory {

    private Object object;

    public ValidatorFactory(Object object){
        this.object = object;
    }

    public ModelValidator getValidator(){
        if(object instanceof Product)
            return new ProductValidator((Product) object);
        else if(object instanceof User)
            return new UserValidator((User) object);
        else if(object instanceof Family)
            return new FamilyValidator((Family) object);
        else
            return null;
    }
}
