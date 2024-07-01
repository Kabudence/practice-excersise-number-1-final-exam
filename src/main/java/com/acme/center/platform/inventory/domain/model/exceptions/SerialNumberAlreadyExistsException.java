package com.acme.center.platform.inventory.domain.model.exceptions;

public class SerialNumberAlreadyExistsException extends RuntimeException{
    public SerialNumberAlreadyExistsException(String serialNumber) {
        super("Another product with the same serial number: " + serialNumber + " already exists");
    }
}
