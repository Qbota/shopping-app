package com.assignments.api.util;

import java.util.UUID;

public class CodeGenerator {

    public static String generateCode() {
        return UUID.randomUUID().toString();
    }
}
