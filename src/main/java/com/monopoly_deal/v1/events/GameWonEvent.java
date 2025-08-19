package com.monopoly_deal.v1.events;

public class GameWonEvent extends GameEvent {
    private final String winnerName;

    public GameWonEvent(Object source, String gameId, String winnerName) {
        super(source, gameId, winnerName + " wins the game!");
        this.winnerName = winnerName;
    }

    public String getWinnerName() {
        return winnerName;
    }
}