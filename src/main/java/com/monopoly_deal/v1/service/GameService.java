package com.monopoly_deal.v1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monopoly_deal.v1.engine.GameEngine;
import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.engine.TurnManager;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Deck;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.utils.CardLoader;

@Service
public class GameService {
    
    private GameState gameState;
    private final GameEngine gameEngine;
    private final TurnManager turnManager;

    public GameService() {
        this.gameEngine = new GameEngine();
        this.turnManager = new TurnManager();
    }

    // start a new game
    /*
     * create players and initialize deck first
     * 
     */
    public void startGame(List<Player> players, Deck deck) {
        gameState = new GameState(players, deck);
        gameEngine.startGame(gameState);
    }

    // draw cards at the start of a player's turn
    public void drawCardsForCurrentPlayer() {
        Player currPlayer = gameState.getCurrentPlayer();
        gameEngine.drawCard(gameState, currPlayer, false);
        gameEngine.drawCard(gameState, currPlayer, false);
    }

    // play a card (or bank it)
    public void playCard(String playerName, Card card, boolean playAsMoney) {
        Player player = gameState.getPlayerByName(playerName);
        gameEngine.playCard(gameState, player, card, playAsMoney);
    }

    // end current player's turn
    public void endTurn() {
        turnManager.endTurn(gameState);
    }

    // get current game state (readOnly)
    public GameState getGameState() {
        return gameState;
    }
}
