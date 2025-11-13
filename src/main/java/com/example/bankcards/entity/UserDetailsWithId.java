package com.example.bankcards.entity;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsWithId extends UserDetails {
    long getId();
}
