package com.acme.center.platform.iam.domain.model.queries;

import com.acme.center.platform.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}
