package com.acme.center.platform.learning.interfaces.rest.resources;

public record CreateStudentResource(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) {
}