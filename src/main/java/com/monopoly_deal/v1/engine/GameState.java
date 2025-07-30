package com.monopoly_deal.v1.engine;

import java.util.List;

import com.monopoly_deal.v1.model.*;

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


    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    public DiscardPile getDiscardPile() {
        return discardPile;
    }
    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }
    public GamePhase getCurrentPhase() {
        return currentPhase;
    }
    public void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    
}
