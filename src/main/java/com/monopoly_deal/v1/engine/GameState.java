package com.monopoly_deal.v1.engine;

import java.util.List;

import com.monopoly_deal.v1.enums.GamePhase;
import com.monopoly_deal.v1.model.*;

import lombok.Data;

@Data
public class GameState {
    private List<Player> players;
    private int currentPlayerIndex;
    private Deck deck;
    private DiscardPile discardPile;
    private GamePhase currentPhase;
    private boolean gameOver;

    // constructor
    public GameState(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        this.discardPile = new DiscardPile();
        this.currentPlayerIndex = 0;
        this.currentPhase = GamePhase.DRAW_PHASE;
        this.gameOver = false;
    }

    // utility methods
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
}
