package com.example.bankcards.util;


import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserDtoConverter implements Converter<User, GetUserDto> {
    @Autowired
    CardToGetCardAsAdminDtoConverter cardToGetCardAsAdminDtoConverter;

    @Override
    public GetUserDto convert(User source) {
        GetUserDto dto = new GetUserDto();
        dto.id = source.getId();
        dto.role = source.getRole();
        dto.username = source.getUsername();
        dto.cards = source.getCards().stream().map(c -> cardToGetCardAsAdminDtoConverter.convert(c).number).toList();
        return dto;
    }
}
