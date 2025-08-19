package com.monopoly_deal.v1.events;

public class GameStartedEvent extends GameEvent {
    private final int playerCount;

    public GameStartedEvent(Object source, String gameId, int playerCount) {
        super(source, gameId, "Game started with " + playerCount + " players");
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}