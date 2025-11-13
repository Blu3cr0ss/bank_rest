package com.example.bankcards.util;

import com.example.bankcards.dto.GetCardDto;
import com.example.bankcards.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardToGetCardDtoConverter implements Converter<Card, GetCardDto> {
    @Override
    public GetCardDto convert(Card source) {
        GetCardDto dto = new GetCardDto();
        dto.expiration = source.getExpiration().toString();
        dto.owner = source.getOwner().getId();
        dto.status = source.getStatus();
        dto.balance=source.getBalance();
        StringBuilder sb = new StringBuilder(String.valueOf(source.getNumber()));
        sb.reverse();
        long needZeros = 16 - sb.length();
        for (int i = 0; i < needZeros; i++) sb.append("0");
        sb.reverse();
        sb.insert(4, " ");
        sb.insert(9, " ");
        sb.insert(14, " ");
        sb.insert(19, " ");
        dto.number = sb.toString();
        return dto;
    }
}
