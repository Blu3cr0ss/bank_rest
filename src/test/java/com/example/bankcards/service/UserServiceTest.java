package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CardRepository cardRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @InjectMocks
    private UserService userService;

    @Test
    void tryAuthenticate() {
        String ret = passwordEncoder.encode("qwe");
        when(userRepository.getPasswordByUsername("qwe")).thenReturn(ret);

        assertTrue(userService.tryAuthenticate("qwe", "qwe"));
        assertFalse(userService.tryAuthenticate("invalid", "qwe"));
        assertFalse(userService.tryAuthenticate("qwe", "invalid"));
    }

    @Test
    void transfer_verifies_user() throws Exception {
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(0L);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.transfer(1, 2, 50, 0);
            }
        });
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(1L);
        assertThrows(ApiException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.transfer(1, 2, 50, 0);
            }
        });
    }

    @Test
    void requestBlock_verifies_user() {
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(0L);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.requestBlock(0, 0);
            }
        });
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(1L);
        assertThrows(ApiException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.requestBlock(0, 0);
            }
        });
    }

    @Test
    void getBalance_verifies_user() {
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(0L);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.getBalance(0, 0);
            }
        });
        when(cardRepository.getOwnerIdByNumber(any(Long.class))).thenReturn(1L);
        assertThrows(ApiException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.getBalance(0, 0);
            }
        });
    }
}
