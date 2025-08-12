package com.monopoly_deal.v1.service;

import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.enums.CardType;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.context.ActionContext;
import com.monopoly_deal.v1.service.strategies.CardActionStrategy;
import com.monopoly_deal.v1.service.strategies.CardActionStrategyFactory;

import java.util.List;

public class CardActionService {


    public void playCard(GameState gameState, Player player, Card card, boolean playAsMoney, List<String> targetPlayerIds) {
        if(!player.getHand().contains(card)) {
            throw new IllegalArgumentException("Player doesn't have this card in hand");
        }

        if(playAsMoney) {
            if(card.getCardType() == CardType.PROPERTY) {
                throw new IllegalArgumentException("Property cards cannot be played as money");
            }
            player.addToBank(card);
            player.getHand().removeIf(c -> c.getId().equals(card.getId()));
            return;
        }

        CardActionStrategy strategy = CardActionStrategyFactory.getStrategy(card.getCardType());
        ActionContext context = new ActionContext(gameState, player, card, targetPlayerIds);
        strategy.execute(context);

        player.getHand().removeIf(c -> c.getId().equals(card.getId()));
    }
}
