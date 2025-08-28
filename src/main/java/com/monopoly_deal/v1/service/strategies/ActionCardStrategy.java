package com.monopoly_deal.v1.service.strategies;

import java.util.HashMap;
import java.util.Map;

import com.monopoly_deal.v1.enums.ActionType;
import com.monopoly_deal.v1.model.ActionCard;
import com.monopoly_deal.v1.model.PendingAction;
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
        String targetPlayerId = context.getRequest().getTargetPlayerIds().get(0);
        String targetCardId = context.getRequest().getTargetCardId();
        
        Map<String,Object> payload = new HashMap<>();
        payload.put("targetCardId", targetCardId);

        PendingAction pendingAction = new PendingAction(requestingPlayerId, targetPlayerId, payload, ActionType.SLY_DEAL);
        context.getGameState().getPendingActions().add(pendingAction);
    }
}
