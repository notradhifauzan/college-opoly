package com.monopoly_deal.v1.service.strategies;

import com.monopoly_deal.v1.enums.CardType;

public class CardActionStrategyFactory {
    public static CardActionStrategy getStrategy(CardType cardType) {
        switch (cardType) {
            case PROPERTY:
                return new PropertyCardStrategy();
            case ACTION:
                return new ActionCardStrategy();
            case RENT:
                return new RentCardStrategy();
            default:
                throw new IllegalArgumentException("Unsupported card type: " + cardType);
        }
    }
}
