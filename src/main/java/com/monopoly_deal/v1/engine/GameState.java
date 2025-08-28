package com.monopoly_deal.v1.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.monopoly_deal.v1.enums.GamePhase;
import com.monopoly_deal.v1.model.*;

import lombok.Data;

@Data
public class GameState {
    private final int MAX_MOVES_PER_TURN = 3;
    
    private List<Player> players;
    private int currentPlayerIndex;
    private int currentPlayerMoves;
    private Deck deck;
    private DiscardPile discardPile;
    private GamePhase currentPhase;
    private boolean gameOver;
    private List<PendingAction> pendingActions;

    // constructor
    public GameState(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        this.discardPile = new DiscardPile();
        this.currentPlayerIndex = 0;
        this.currentPlayerMoves = 0;
        this.currentPhase = GamePhase.DRAW_PHASE;
        this.gameOver = false;
        this.pendingActions = new ArrayList<>();
    }

    // utility methods
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void advanceToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void incrementMoves() {
        this.currentPlayerMoves += 1;
    }

    public void resetMoves() {
        this.currentPlayerMoves = 0;
    }

    public Player getPlayerByName(String name) {
        return players.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Player not found"));
    }
}
