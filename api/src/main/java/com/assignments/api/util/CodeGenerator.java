package com.assignments.api.util;

import java.util.UUID;

public final class CodeGenerator {

    private CodeGenerator(){
        //Should be empty
    }
    public static String generateCode() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
