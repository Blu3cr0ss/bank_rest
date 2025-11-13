package com.example.bankcards.controller;

import com.example.bankcards.config.SecurityConfig;
import com.example.bankcards.dto.GetCardDto;
import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.service.AdminService;
import com.example.bankcards.service.JwtService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {
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

    @Test
    void getCards() throws Exception {
        GetCardDto card1 = new GetCardDto();
        card1.number = "1";
        GetCardDto card2 = new GetCardDto();
        card2.number = "2";
        when(userService.getCards(1)).thenReturn(List.of(card1, card2));
        when(userService.getCardsLimited(1, 1, 1)).thenReturn(List.of(card2));

        mvc.perform(
                        get("/api/user/cards")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("1"))
        ;
        mvc.perform(
                        get("/api/user/cards")
                                .param("limit","1")
                                .param("offset","1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwidXNlciI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.K4djI6z3ZUd72OoI8hJhNQytDWADuwCm6pb5FC9wbYY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value("2"))
        ;
    }
}
