package com.monopoly_deal.v1.service;

import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.model.PropertyCard;

public class CardActionService {
    

    public void playCard(GameState gameState, Player player, Card card, boolean playAsMoney) {
        if(!player.getHand().contains(card)) {
            throw new IllegalArgumentException("Player doesn't have this card in hand");
        }

        if(playAsMoney) {
            player.addToBank(card);
            System.out.println(player.getName() + " banked card: " + card.getName() + " ($" + card.getValue() + ")");
            player.getHand().remove(card);
            return;
        }

        switch(card.getCardType()) {
            case PROPERTY:
                if(!(card instanceof PropertyCard)) {
                    throw new IllegalStateException("Expected PropertyCard, got " + card.getClass().getSimpleName());
                }

                // TODO: how to put wild card, house, hotel?
                // does a wild card ALWAYS put on top of property card?
                player.addPropertyCard((PropertyCard) card);

            case ACTION:
            case RENT:
                // For now, we just discard it
                gameState.getDiscardPile().add(card);
                System.out.println(player.getName() + " played action/rent card: " + card.getName());
                break;
            default:
                throw new IllegalArgumentException("Unsupported card type: " + card.getCardType());
        }

        player.getHand().remove(card);
    }
}
