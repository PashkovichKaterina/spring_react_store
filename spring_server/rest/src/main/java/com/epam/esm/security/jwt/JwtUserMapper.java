package com.epam.esm.security.jwt;

import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserMapper {

    public JwtUserMapper() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getLogin(), user.getEmail(), user.getPassword(), mapToGrantedAuthorities(user.getRole()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(UserRole userRole) {
        List<GrantedAuthority> grantedAuthority = new ArrayList<>();
        grantedAuthority.add(new SimpleGrantedAuthority("ROLE_" + userRole.toString()));
        return grantedAuthority;
    }
}