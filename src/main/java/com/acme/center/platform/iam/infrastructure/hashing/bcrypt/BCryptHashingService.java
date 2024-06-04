package com.acme.center.platform.iam.infrastructure.hashing.bcrypt;

import com.acme.center.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}
