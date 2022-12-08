package ru.otus.spring.model;

import java.util.UUID;

public class User {
    private Long id;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String firstName, String lastName) {
        this(UUID.randomUUID().getLeastSignificantBits(), firstName, lastName);
    }

    public User(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }
}
