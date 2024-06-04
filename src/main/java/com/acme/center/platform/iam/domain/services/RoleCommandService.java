package com.acme.center.platform.iam.domain.services;

import com.acme.center.platform.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
