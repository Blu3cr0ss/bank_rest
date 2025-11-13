package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class JwtServiceTest {
    JwtService jwtService = new JwtService("mKwsNBdMVL0bpdcLDhpc5UQoK2xrUOj4DJPPTfQ5MlOpa");
    @Test
    void testGen() {
        Assertions.assertEquals(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJ1c2VyIjoidGVzdCIsInJvbGUiOiJVU0VSIn0.LuRUASOBqLN4b3d_pyHc8-N1Od_LiF0WMhaeRxWKJCg",
                jwtService.generate(123, "test", Role.USER)
        );
    }

    @Test
    void testVerify() {
        Assertions.assertEquals("test", jwtService.verify(
                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJ1c2VyIjoidGVzdCIsInJvbGUiOiJVU0VSIn0.LuRUASOBqLN4b3d_pyHc8-N1Od_LiF0WMhaeRxWKJCg"
        ).username);
    }

}
