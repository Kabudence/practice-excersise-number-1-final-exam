package com.acme.center.platform.profiles.domain.model.commands;

public record CreateProfileCommand(String firstName, String lastName, String email, String street, String number, String city, String postalCode, String country) { }
