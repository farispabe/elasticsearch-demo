package com.elasticsearch.example.demo.model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private String internalValue;

    Gender(String internalValue) {
        this.internalValue = internalValue;
    }

    public String getInternalValue() {
        return internalValue;
    }
}
