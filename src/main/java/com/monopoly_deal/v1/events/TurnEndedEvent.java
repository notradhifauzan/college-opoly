package com.monopoly_deal.v1.events;

public class TurnEndedEvent extends GameEvent {
    private final String nextPlayerName;

    public TurnEndedEvent(Object source, String gameId, String nextPlayerName) {
        super(source, gameId, "Turn ended. Next player: " + nextPlayerName);
        this.nextPlayerName = nextPlayerName;
    }

    public String getNextPlayerName() {
        return nextPlayerName;
    }
}