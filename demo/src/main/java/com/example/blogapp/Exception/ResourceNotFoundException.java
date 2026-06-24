package com.example.blogapp.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s : %s",
                resourceName,
                fieldName,
                fieldValue));
    }
}

