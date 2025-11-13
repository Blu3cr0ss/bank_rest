package com.example.bankcards.util;

import com.example.bankcards.dto.GetCardAsAdminDto;
import com.example.bankcards.entity.Card;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardToGetCardAsAdminDtoConverter implements Converter<Card, GetCardAsAdminDto> {
    @Override
    public GetCardAsAdminDto convert(Card source) {
        GetCardAsAdminDto dto = new GetCardAsAdminDto();
        dto.expiration = source.getExpiration().toString();
        dto.owner = source.getOwner().getId();
        dto.status = source.getStatus();
        StringBuilder sb = new StringBuilder(String.valueOf(source.getNumber()));
        sb.reverse();
        long needZeros = 4 - sb.length();
        for (int i = 0; i < needZeros; i++) sb.append("0");
        long needStars = 16 - sb.length();
        for (int i = 0; i < needStars; i++) sb.append("*");
        sb.reverse();
        sb.insert(4, " ");
        sb.insert(9, " ");
        sb.insert(14, " ");
        sb.insert(19, " ");
        dto.number = sb.toString();
//        dto.number = "**** **** **** **** " + source.getNumber() % 10000;
        return dto;
    }
}
