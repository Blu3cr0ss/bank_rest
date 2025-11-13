package com.example.bankcards.service;

import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.dto.CreateUserDto;
import com.example.bankcards.dto.GetCardAsAdminDto;
import com.example.bankcards.dto.GetUserDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardToGetCardAsAdminDtoConverter;
import com.example.bankcards.util.CreateCardDtoToCardConverter;
import com.example.bankcards.util.CreateUserDtoToUserConverter;
import com.example.bankcards.util.UserToGetUserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CreateCardDtoToCardConverter createCardDtoToCardConverter;
    @Autowired
    CreateUserDtoToUserConverter createUserDtoToUserConverter;
    @Autowired
    UserToGetUserDtoConverter userToGetUserDtoConverter;
    @Autowired
    CardToGetCardAsAdminDtoConverter cardToGetCardAsAdminDtoConverter;

    public List<GetUserDto> getUsers() {
        return userRepository.findAll().stream().map(userToGetUserDtoConverter::convert).toList();
    }

    public List<GetUserDto> getUsersLimited(int limit, int offset) {
        return userRepository.findAll(limit, offset).stream().map(userToGetUserDtoConverter::convert).toList();
    }

    public GetUserDto createUser(CreateUserDto userDto) {
        return userToGetUserDtoConverter
                .convert(userRepository.save(createUserDtoToUserConverter.convert(userDto)));
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public List<GetCardAsAdminDto> getCards() {
        return cardRepository.findAll().stream().map(cardToGetCardAsAdminDtoConverter::convert).toList();
    }
    public List<GetCardAsAdminDto> getCardsWithMinBalance(int minBalance) {
        return cardRepository.findWithMinBalance(minBalance).stream().map(cardToGetCardAsAdminDtoConverter::convert).toList();
    }

    public List<GetCardAsAdminDto> getCardsLimited(int limit, int offset) {
        return cardRepository.findAll(limit, offset).stream().map(cardToGetCardAsAdminDtoConverter::convert).toList();
    }
    public List<GetCardAsAdminDto> getCardsWithMinBalanceLimited(int limit, int offset,int minBalance) {
        return cardRepository.findWithMinBalance(limit, offset, minBalance).stream().map(cardToGetCardAsAdminDtoConverter::convert).toList();
    }

    public void setCardStatus(Card.Status status, long card) {
        cardRepository.setStatusByNumber(status, card);
    }

    public GetCardAsAdminDto createCard(CreateCardDto cardDto) {
        return cardToGetCardAsAdminDtoConverter.convert(
                cardRepository.save(createCardDtoToCardConverter.convert(cardDto))
        );
    }

    public void deleteCard(long card) {
        cardRepository.deleteById(card);
    }
}
