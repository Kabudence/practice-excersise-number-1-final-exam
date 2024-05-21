package com.acme.center.platform.profiles.domain.model.queries;

import com.acme.center.platform.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
