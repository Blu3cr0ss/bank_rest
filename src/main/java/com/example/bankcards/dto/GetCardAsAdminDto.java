package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;

public class GetCardAsAdminDto {
    public String number;
    public Card.Status status;
    public long owner;
    public String expiration;
}
