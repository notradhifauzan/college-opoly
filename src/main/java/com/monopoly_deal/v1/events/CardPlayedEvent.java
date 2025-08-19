package com.monopoly_deal.v1.events;

public class CardPlayedEvent extends GameEvent {
    private final String playerName;
    private final String cardName;

    public CardPlayedEvent(Object source, String gameId, String playerName, String cardName) {
        super(source, gameId, playerName + " played " + cardName);
        this.playerName = playerName;
        this.cardName = cardName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCardName() {
        return cardName;
    }
}