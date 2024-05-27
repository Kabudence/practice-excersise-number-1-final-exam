package com.acme.center.platform.learning.domain.model.commands;

public record CreateStudentCommand(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
}
