package com.monopoly_deal.v1.engine;

import com.monopoly_deal.v1.enums.GamePhase;
import com.monopoly_deal.v1.model.Player;

public class TurnManager {
    

    public void startTurn(GameState gameState) {
        gameState.setCurrentPhase(GamePhase.DRAW_PHASE);
        System.out.println("Starting turn for: " + gameState.getCurrentPlayer().getName());
    }

    public void endTurn(GameState gameState) {
        System.out.println("Ending turn for: " + gameState.getCurrentPlayer().getName());

        /*
         * TODO: 
         * - Discard down to 7 cards (if needed)
         * - enforce hand size limit
         */

        gameState.advanceToNextPlayer();
        gameState.setCurrentPhase(GamePhase.DRAW_PHASE);
    }

    public boolean isPlayerTurn(GameState gameState, Player player) {
        return gameState.getCurrentPlayer().getId().equals(player.getId());
    }

    public boolean isInPhase(GameState gameState, GamePhase phase) {
        return gameState.getCurrentPhase() == phase;
    }
}
