package com.example.bankcards.util;

import com.example.bankcards.dto.CreateCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateCardDtoToCardConverter implements Converter<CreateCardDto, Card> {
    @Override
    public Card convert(CreateCardDto source) {
        Card card = new Card();
        card.setBalance(source.balance);
        card.setOwner(new User(source.ownerId));
        card.setStatus(Card.Status.ACTIVE);
        return card;
    }
}
