package com.example.bankcards.controller;

import com.example.bankcards.config.SecurityConfig;
import com.example.bankcards.entity.Role;
import com.example.bankcards.security.JwtSecurityFilter;
import com.example.bankcards.service.JwtService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.CreateUserDtoToUserConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.convention.TestBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {
    @TestConfiguration
    static class JwtServiceTestConfig {
        @Bean
        public JwtService jwtService() {
            return new JwtService("mKwsNBdMVL0bpdcLDhpc5UQoK2xrUOj4DJPPTfQ5MlOpa");
        }
    }

    @Autowired
    MockMvc mvc;
    @MockitoBean
    @Mock
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Test
    void login_with_valid_and_invalid_credentials() throws Exception {
        Assertions.assertNotNull(jwtService.generate(1L, "admin", Role.ADMIN));
        when(userService.tryAuthenticate("admin", "admin")).thenReturn(true);
        when(userService.getIdByUsername("admin")).thenReturn(1L);
        when(userService.getRoleById(1L)).thenReturn(Role.ADMIN);

        mvc.perform(
                        post("/api/auth/login")
                                .param("username", "admin")
                                .param("password", "admin")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jwtService.generate(1L, "admin", Role.ADMIN)))
        ;
        mvc.perform(
                        post("/api/auth/login")
                                .param("username", "invalid")
                                .param("password", "invalid")
                )
                .andExpect(status().isUnauthorized())
        ;
    }
}
