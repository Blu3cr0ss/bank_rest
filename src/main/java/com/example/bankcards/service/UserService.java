package com.example.bankcards.service;

import com.example.bankcards.dto.GetCardDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.ApiException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardToGetCardDtoConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    CardRepository cardRepository;
    PasswordEncoder passwordEncoder;
    CardToGetCardDtoConverter cardToGetCardDtoConverter;

    public UserService(
            @Autowired
            UserRepository userRepository,
            @Autowired
            CardRepository cardRepository,
            @Autowired
            PasswordEncoder passwordEncoder,
            @Autowired
            CardToGetCardDtoConverter cardToGetCardDtoConverter
    ) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
        this.cardToGetCardDtoConverter = cardToGetCardDtoConverter;
    }


    public boolean tryAuthenticate(String username, String password) {
        return passwordEncoder.matches(password, userRepository.getPasswordByUsername(username));
    }

    public long getIdByUsername(String username) {
        return userRepository.getIdByUsername(username);
    }

    public Role getRoleById(long id) {
        return userRepository.getRoleById(id);
    }

    public List<GetCardDto> getCards(long userId) {
        return userRepository.getCardsById(userId).stream().map(cardToGetCardDtoConverter::convert).toList();
    }

    public List<GetCardDto> getCardsLimited(long userId, int limit, int offset) {
        return userRepository.getCardsById(userId, limit, offset).stream().map(cardToGetCardDtoConverter::convert).toList();
    }

    @Transactional
    public void transfer(long from, long to, int amount, long userId) {
        long ownerId = cardRepository.getOwnerIdByNumber(from);
        if (ownerId == cardRepository.getOwnerIdByNumber(to) && userId == ownerId) {
            cardRepository.decreaseBalanceByNumber(amount, from);
            cardRepository.increaseBalanceByNumber(amount, to);
        } else throw new ApiException("Cant manage other's cards", HttpStatus.FORBIDDEN);
    }

    public void requestBlock(long card, long userId) {
        if (userId == cardRepository.getOwnerIdByNumber(card))
            cardRepository.setBlockRequestedByNumber(true, card);
        else throw new ApiException("Cant manage other's cards", HttpStatus.FORBIDDEN);
    }

    public int getBalance(long card, long userId) {
        if (userId == cardRepository.getOwnerIdByNumber(card))
            return cardRepository.getBalanceByNumber(card);
        else throw new ApiException("Cant manage other's cards", HttpStatus.FORBIDDEN);
    }
}
