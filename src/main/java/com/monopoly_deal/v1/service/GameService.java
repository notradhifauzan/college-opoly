package com.monopoly_deal.v1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monopoly_deal.v1.dto.PlayCardRequest;
import com.monopoly_deal.v1.engine.GameEngine;
import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Deck;
import com.monopoly_deal.v1.model.Player;

@Service
public class GameService {
    
    private GameState gameState;
    private final GameEngine gameEngine;

    @Autowired
    public GameService(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
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
        gameEngine.drawCard(gameState, currPlayer, true);
        gameEngine.drawCard(gameState, currPlayer, false);
    }

    public void playCardById(String playerName, PlayCardRequest request) {
        Player player = gameState.getPlayerByName(playerName);
        Card card = player.getHand().stream()
            .filter(c -> c.getId().equals(request.getCardId()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Card with ID " + request.getCardId() + " not found in player's hand"));
        gameEngine.playCard(gameState, player, card, request);
    }

    // end current player's turn
    public void endTurn() {
        gameEngine.endTurn(gameState);
    }

    // get current game state (readOnly)
    public GameState getGameState() {
        return gameState;
    }

    // get current player
    public Player getCurrentPlayer() {
        return gameState.getCurrentPlayer();
    }
}
