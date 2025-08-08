package com.monopoly_deal.v1.service.strategies;

import com.monopoly_deal.v1.service.context.ActionContext;

public class ActionCardStrategy implements CardActionStrategy {
    @Override
    public void execute(ActionContext context) {
        // For now, we just discard it
        context.getGameState().getDiscardPile().add(context.getCard());
        System.out.println(context.getPlayer().getName() + " played action card: " + context.getCard().getName());
    }
}
