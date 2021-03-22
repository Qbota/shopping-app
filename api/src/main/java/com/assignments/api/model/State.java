package com.assignments.api.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum State {

    TO_DO("To Do"), IN_PROGRESS("In Progress"), DONE("Done");

    private final String label;

    private State(String label){
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
