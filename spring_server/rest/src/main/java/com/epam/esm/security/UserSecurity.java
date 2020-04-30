package com.epam.esm.security;

import com.epam.esm.security.jwt.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {
    public boolean hasUserId(Authentication authentication, String id) {
        return authentication.getPrincipal() instanceof JwtUser
                && ((JwtUser) authentication.getPrincipal()).getId().equals(Long.parseLong(id));
    }
}