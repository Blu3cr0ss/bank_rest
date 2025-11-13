package com.example.bankcards.util;

import com.example.bankcards.dto.CreateUserDto;
import com.example.bankcards.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUserDtoToUserConverter implements Converter<CreateUserDto, User> {
    @Autowired
    PasswordEncoder passwordEncoder;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public User convert(CreateUserDto source) {
        User user = new User();
        user.setRole(source.role);
        user.setUsername(source.username);
        user.setPassword(getPasswordEncoder().encode(source.password));
        return null;
    }
}
