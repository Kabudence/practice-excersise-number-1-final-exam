package com.acme.center.platform.iam.domain.model.commands;

import com.acme.center.platform.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
