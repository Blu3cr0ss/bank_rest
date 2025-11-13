package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;

public class GetCardDto {
    public String number;
    public int balance;
    public Card.Status status;
    public long owner;
    public String expiration;
}
