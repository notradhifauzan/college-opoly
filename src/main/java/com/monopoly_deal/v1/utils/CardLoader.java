package com.monopoly_deal.v1.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.PropertyCard;

public class CardLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Card> loadAllCards() {
        List<Card> allCards = new ArrayList<>();

        allCards.addAll(loadPropertyCards());

        return allCards;
    }

    private static <T> List<PropertyCard> loadPropertyCards() {
        return readCards("/cards/property-card.json", new TypeReference<List<PropertyCard>>() {});
    }

    private static <T extends Card> List<T> readCards(String path, TypeReference<List<T>> typeRef) {
        try (InputStream is = CardLoader.class.getResourceAsStream(path)) {
            return mapper.readValue(is, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
