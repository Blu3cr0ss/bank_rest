package com.example.bankcards.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.dto.CreateUserDto;
import com.example.bankcards.dto.GetCardAsAdminDto;
import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardToGetCardAsAdminDtoConverter;
import com.example.bankcards.util.CreateCardDtoToCardConverter;
import com.example.bankcards.util.CreateUserDtoToUserConverter;
import com.example.bankcards.util.UserToGetUserDtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    UserRepository userRepository;
    @Mock
    CardRepository cardRepository;
    @Spy
    UserToGetUserDtoConverter userToGetUserDtoConverter;
    @Spy
    CardToGetCardAsAdminDtoConverter cardToGetCardAsAdminDtoConverter;


    @Test
    void getUsers_returns_valid() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("qwe");
        user1.setRole(Role.USER);
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("asd");
        user2.setRole(Role.USER);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<GetUserDto> users = adminService.getUsers();

        assertEquals(2, users.size());
        assertEquals(user1.getRole(), users.get(0).role);
    }

    @Test
    void getCards_returns_valid() {
        User user = new User();
        user.setId(1L);

        Card card1 = new Card();
        card1.setStatus(Card.Status.BLOCKED);
        card1.setOwner(user);
        Card card2 = new Card();
        card2.setStatus(Card.Status.ACTIVE);
        card2.setOwner(user);

        when(cardRepository.findAll()).thenReturn(Arrays.asList(card1, card2));

        List<GetCardAsAdminDto> cards = adminService.getCards();

        assertEquals(2, cards.size());
        assertEquals(Card.Status.BLOCKED, cards.get(0).status);
    }
}
