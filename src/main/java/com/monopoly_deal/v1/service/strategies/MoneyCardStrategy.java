package com.monopoly_deal.v1.service.strategies;

import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.context.ActionContext;

public class MoneyCardStrategy implements CardActionStrategy {

    @Override
    public void execute(ActionContext context) {
        Player player = context.getGameState().getCurrentPlayer();
        Card playCard = context.getCard();

        player.addToBank(playCard);
        player.getHand().removeIf(c -> c.getId().equals(playCard.getId()));
    }
}
