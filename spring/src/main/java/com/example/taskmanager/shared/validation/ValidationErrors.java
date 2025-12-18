package com.example.taskmanager.shared.validation;

import java.util.List;

public class ValidationErrors {

    private List<String> errors;

    public ValidationErrors() {
    }

    public ValidationErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}


