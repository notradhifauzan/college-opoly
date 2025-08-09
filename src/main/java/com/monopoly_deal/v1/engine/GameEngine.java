package com.monopoly_deal.v1.engine;

import com.monopoly_deal.v1.enums.GamePhase;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.CardActionService;

import java.util.List;

public class GameEngine {
    private final TurnManager turnManager = new TurnManager();
    private final CardActionService cardActionService = new CardActionService();

    public void startGame(GameState gameState) {
        // each player draws 5 cards initially
        for(Player player: gameState.getPlayers()) {
            for(int i=0;i<5;i++) {
                drawCard(gameState, player, true);
            }
        }

        // start the first turn
        turnManager.startTurn(gameState);
    }

    public void drawCard(GameState gameState, Player player, boolean isSetup) {
        if(!isSetup) {
            if(!turnManager.isPlayerTurn(gameState, player)) {
                throw new IllegalStateException("It's not your turn!");
            }
        }

        if(gameState.getCurrentPhase() != GamePhase.DRAW_PHASE) {
            throw new IllegalStateException("You can only draw during the draw phase.");
        }

        // draws a card
        Card drawn = gameState.getDeck().draw();
        if(drawn != null) {
            player.addToHand(drawn);
            System.out.println(player.getName() + " drew card: " + drawn.getName());
        }

        if(!isSetup) {
            gameState.setCurrentPhase(GamePhase.PLAY_PHASE);
        }
    }

    public void playCard(GameState gameState, Player player, Card card, boolean playAsMoney, List<String> targetPlayerIds) {
        if(!turnManager.isPlayerTurn(gameState, player)) {
            throw new IllegalStateException("It's not your turn!");
        }

        if(gameState.getCurrentPhase() != GamePhase.PLAY_PHASE) {
            throw new IllegalStateException("You can only play cards during the play phase.");
        }

        if(!player.getHand().contains(card)) {
            throw new IllegalStateException("You don't have that card. ");
        }

        cardActionService.playCard(gameState, player, card, playAsMoney, targetPlayerIds);
        checkWinCondition(gameState);
    }

    public void endTurn(GameState gameState) {
        turnManager.endTurn(gameState);
    }

    public boolean checkWinCondition(GameState gameState) {
        for(Player player: gameState.getPlayers()) {
            long completeSets = player.getPropertySets().stream().filter(set -> set.getCards().size() >= 3).count();

            if(completeSets >= 3){
                gameState.setGameOver(true);
                System.out.println(player.getName() + " Wins the game!");
                return true;
            }
        }

        return false;
    }
}
