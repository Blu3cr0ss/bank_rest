package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.UserDetailsWithId;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserJwtDto implements UserDetailsWithId {
    public long id;
    public String username;
    public Role role;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }
}
