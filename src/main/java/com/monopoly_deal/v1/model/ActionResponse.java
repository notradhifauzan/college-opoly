package com.monopoly_deal.v1.model;

public class ActionResponse {

    private final Player player;
    private final PendingAction pendingAction;
    private final Card counterCard;
    private final Boolean accept;

    public ActionResponse(Player player, PendingAction pendingAction, Card counterCard, Boolean accept) {
        this.accept = accept;
        this.player = player;
        this.pendingAction = pendingAction;
        this.counterCard = !accept ? counterCard : null;
    }

    public Player getPlayer() {
        return player;
    }
    public PendingAction getPendingAction() {
        return pendingAction;
    }
    public Card getCounterCard() {
        return counterCard;
    }
    public Boolean getAccept() {
        return accept;
    }

    
}
