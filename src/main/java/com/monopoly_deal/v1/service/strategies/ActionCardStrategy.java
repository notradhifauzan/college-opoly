package com.monopoly_deal.v1.service.strategies;

import com.monopoly_deal.v1.model.ActionCard;
import com.monopoly_deal.v1.service.context.ActionContext;

public class ActionCardStrategy implements CardActionStrategy {
    @Override
    public void execute(ActionContext context) {

        ActionCard actionCard = (ActionCard) context.getCard();
        switch (actionCard.getActionType()) {
            case SLY_DEAL:
                playSlyDeal(context);
                break;
            default:
                break;
        }

        context.getGameState().getDiscardPile().add(context.getCard());
        System.out.println(context.getPlayer().getName() + " played action card: " + context.getCard().getName());
    }

    private void playSlyDeal(ActionContext context) {
        String requestingPlayerId = context.getPlayer().getId();
        String targetPlayerId = context.getTargetCardId();
        String targetCardId = context.getRequest().getTargetCardId();

        // find card in targetPlayerid's property
        // remove from targetPlayerId's property
        // add into requestingPlayerId's property
    }
}
