package com.shopping.api.validator;


class AbstractValidator<T> implements Validator{

    public T object;

    public AbstractValidator(T object){
        this.object = object;
    }

    @Override
    public boolean isObjectNotValid() {
        if(this.object == null)
            return true;
        return false;
    }

    @Override
    public boolean isStringAttributeNotValid(String attribute) {
        if(attribute == null || attribute.isBlank() || attribute.isEmpty())
            return true;
        return false;
    }
}
