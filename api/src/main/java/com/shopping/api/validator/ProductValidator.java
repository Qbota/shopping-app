package com.shopping.api.validator;

import com.shopping.api.model.Product;

class ProductValidator extends AbstractValidator<Product> implements ModelValidator {
    public ProductValidator(Product object) {
        super(object);
    }

    @Override
    public boolean validate() {
        if(isObjectNotValid())
            return false;
        if(isStringAttributeNotValid(object.getName()))
            return false;
        return true;
    }
}
