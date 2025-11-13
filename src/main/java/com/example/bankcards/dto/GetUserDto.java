package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;

import java.util.List;

public class GetUserDto {
    public long id;
    public Role role;
    public String username;
    public List<String> cards;
}
