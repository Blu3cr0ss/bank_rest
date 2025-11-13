package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.dto.CreateUserDto;
import com.example.bankcards.dto.GetCardAsAdminDto;
import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping("/users")
    public List<GetUserDto> getUsers(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        if (limit != null && offset != null) return adminService.getUsersLimited(limit, offset);
        return adminService.getUsers();
    }

    @PostMapping("/user")
    public GetUserDto createUser(CreateUserDto user) {
        return adminService.createUser(user);
    }

    @DeleteMapping("/user")
    public void deleteUserById(long id) {
        adminService.deleteUserById(id);
    }

    @GetMapping("/cards")
    public List<GetCardAsAdminDto> getCards(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer minBalance) {
        // знаю что можно юзать например Criteria чтоб комбинировать фильтры но я прокрастинировал и пришлось делать недельное задание за 3 дня и я подумал так будет быстрее :)
        if (minBalance != null)
            if (limit != null && offset != null)
                return adminService.getCardsWithMinBalanceLimited(limit, offset, minBalance);
            else return adminService.getCardsWithMinBalance(minBalance);
        if (limit != null && offset != null) return adminService.getCardsLimited(limit, offset);
        return adminService.getCards();
    }

    @PutMapping("/card/status")
    public void setCardStatus(Card.Status status, long card) {
        adminService.setCardStatus(status, card);
    }

    @PostMapping("/card")
    public GetCardAsAdminDto createCard(CreateCardDto card) {
        return adminService.createCard(card);
    }

    @DeleteMapping("/card")
    public void deleteCard(long card) {
        adminService.deleteCard(card);
    }
}
