package com.acme.center.platform.inventory.domain.model.exceptions;

import java.util.Date;

public class InstallationDateCannotPastException extends RuntimeException{
    public InstallationDateCannotPastException(Date installationDate) {
        super("Installation date: " + installationDate + " cannot be in the past" );
    }
}
