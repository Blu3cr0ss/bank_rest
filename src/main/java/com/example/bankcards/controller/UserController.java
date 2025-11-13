package com.example.bankcards.controller;

import com.example.bankcards.dto.GetCardDto;
import com.example.bankcards.entity.UserDetailsWithId;
import com.example.bankcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/cards")
    public List<GetCardDto> getCards(@AuthenticationPrincipal UserDetailsWithId userDetailsWithId, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        if (limit != null && offset != null)
            return userService.getCardsLimited(userDetailsWithId.getId(), limit, offset);
        return userService.getCards(userDetailsWithId.getId());
    }


    @PostMapping("/transfer")
    public void transfer(long from, long to, int amount, @AuthenticationPrincipal UserDetailsWithId userDetailsWithId) {
        userService.transfer(from, to, amount, userDetailsWithId.getId());
    }

    @PostMapping("/requestBlock")
    public void requestBlock(long card, @AuthenticationPrincipal UserDetailsWithId userDetailsWithId) {
        userService.requestBlock(card, userDetailsWithId.getId());
    }

    @GetMapping("/balance")
    public int getBalance(long card, @AuthenticationPrincipal UserDetailsWithId userDetailsWithId) {
        return userService.getBalance(card, userDetailsWithId.getId());
    }
}
