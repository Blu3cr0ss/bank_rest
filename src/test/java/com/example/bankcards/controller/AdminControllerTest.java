package com.example.bankcards.controller;

import com.example.bankcards.config.SecurityConfig;
import com.example.bankcards.dto.GetCardAsAdminDto;
import com.example.bankcards.dto.GetCardDto;
import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.security.JwtSecurityFilter;
import com.example.bankcards.service.AdminService;
import com.example.bankcards.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(value = AdminController.class)
@Import(SecurityConfig.class)
public class AdminControllerTest {
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
    AdminService adminService;

    @Test
    void getUsers() throws Exception {
        GetUserDto user = new GetUserDto();
        user.username = "admin";
        user.id = 1;
        user.role = Role.ADMIN;
        when(adminService.getUsers()).thenReturn(List.of(user));

        mvc.perform(
                        get("/api/admin/users")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin"))
        ;
    }

    @Test
    void getCards() throws Exception {
        GetCardAsAdminDto card1 = new GetCardAsAdminDto();
        card1.number = "1";
        GetCardAsAdminDto card2 = new GetCardAsAdminDto();
        card2.number = "2";
        GetCardAsAdminDto card3 = new GetCardAsAdminDto();
        card3.number = "3";

        when(adminService.getCards()).thenReturn(List.of(card1, card2, card3));
        when(adminService.getCardsLimited(1, 1)).thenReturn(List.of(card2));
        when(adminService.getCardsWithMinBalance(10)).thenReturn(List.of(card2, card3));
        when(adminService.getCardsWithMinBalanceLimited(1, 1, 10)).thenReturn(List.of(card3));

        mvc.perform(
                        get("/api/admin/cards")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
        ;
        mvc.perform(
                        get("/api/admin/cards")
                                .param("limit","1")
                                .param("offset","1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("2"))
        ;
        mvc.perform(
                        get("/api/admin/cards")
                                .param("minBalance","10")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("2"))
                .andExpect(jsonPath("$[1].number").value("3"))

        ;
        mvc.perform(
                        get("/api/admin/cards")
                                .param("minBalance","10")
                                .param("limit","1")
                                .param("offset","1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("3"))
        ;
    }
}
