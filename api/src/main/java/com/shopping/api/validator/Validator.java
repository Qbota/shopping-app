package com.shopping.api.validator;


interface Validator {
    boolean isObjectNotValid();
    boolean isStringAttributeNotValid(String attribute);
}
