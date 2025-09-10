package com.monopoly_deal.v1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.monopoly_deal.v1.dto.PlayCardRequest;
import com.monopoly_deal.v1.model.ActionResponse;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Deck;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.GameService;
import com.monopoly_deal.v1.utils.CardLoader;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private GameService gameService;

    @MessageMapping("/game/join")
    @SendTo("/topic/game/updates")
    public String joinGame(String playerName) {
        return playerName + " joined the game!";
    }
    
    @MessageMapping("/game/start")
    @SendTo("/topic/game/updates")
    public String startGame(List<String> playerNames) {
        try {
            List<Player> players = playerNames.stream().map(Player::new).collect(Collectors.toList());
            Deck deck = new Deck(CardLoader.loadAllCards());
            gameService.startGame(players, deck);
            
            // Broadcast game state to all players
            broadcastGameUpdate(gameService.getGameState());
            
            return "GAME_STARTED:" + String.join(",", playerNames);
        } catch (Exception e) {
            return "GAME_START_ERROR:" + e.getMessage();
        }
    }

     @MessageMapping("/game/end-turn")
    public void endTurn() {
        try {
            if(gameService.getGameState() == null) {
                messagingTemplate.convertAndSend("/topic/game/updates", "PLAY_ERROR:Game not started yet");
                return;
            }
            gameService.endTurn();

            // Broadcast game state to all players
            broadcastGameUpdate(gameService.getGameState());
        } catch (IllegalStateException e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "TURN_ERROR:" + e.getMessage());
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "TURN_ERROR:An error occurred while ending turn");
        }
    }

    @MessageMapping("/game/respond-to-action")
    public void respondToAction(String playerId, boolean accept, String pendingActionId, String counterCardId) {
        try {
            if(gameService.getGameState() == null) {
                messagingTemplate.convertAndSend("/topic/game/updates", "RESPOND_ERROR:Game not started yet");
                return;
            }
            
            // Also broadcast updated game state
            broadcastGameUpdate(gameService.getGameState());
        } catch (IllegalStateException e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "RESPOND_ERROR:" + e.getMessage());
        } catch(Exception e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "RESPOND_ERROR:An error occurred while playing card");
        }

    }

    @MessageMapping("/game/play-card")
    public void playCard(PlayCardRequest request) {
        try {
            if(gameService.getGameState() == null) {
                messagingTemplate.convertAndSend("/topic/game/updates", "PLAY_ERROR:Game not started yet");
                return;
            }
            
            Player currentPlayer = gameService.getCurrentPlayer();
            gameService.playCardById(currentPlayer.getName(), request);
            
            // Also broadcast updated game state
            broadcastGameUpdate(gameService.getGameState());
        } catch (IllegalStateException e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "PLAY_ERROR:" + e.getMessage());
        } catch(Exception e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "PLAY_ERROR:An error occurred while playing card");
        }
    }

    @MessageMapping("/game/draw")
    public void drawCards() {
        try {
            if(gameService.getGameState() == null) {
                messagingTemplate.convertAndSend("/topic/game/updates", "DRAW_ERROR:Game not started yet");
                return;
            }
            
            Player currentPlayer = gameService.getGameState().getCurrentPlayer();
            // get existing player hand
            List<Card> previousCards = new ArrayList<>(currentPlayer.getHand());
            gameService.drawCardsForCurrentPlayer();
            List<Card> currentCards = currentPlayer.getHand();
    
            List<Card> drawnCards = new ArrayList<>(currentCards);
            previousCards.forEach(drawnCards::remove);

            // Create private response with actual card details for the drawing player
            Map<String, Object> privateResponse = new HashMap<>();
            privateResponse.put("type", "DRAW_SUCCESS_PRIVATE");
            privateResponse.put("playerName", currentPlayer.getName());
            privateResponse.put("playerId", currentPlayer.getId());
            privateResponse.put("drawnCards", drawnCards);
            privateResponse.put("totalCardsInHand", currentCards.size());

            // Send private details only to the drawing player
            sendToPlayer(currentPlayer.getId(), privateResponse);

            // Create public response without card details for all players
            Map<String, Object> publicResponse = new HashMap<>();
            publicResponse.put("type", "DRAW_SUCCESS_PUBLIC");
            publicResponse.put("playerName", currentPlayer.getName());
            publicResponse.put("playerId", currentPlayer.getId());
            publicResponse.put("cardsDrawn", drawnCards.size());
            publicResponse.put("totalCardsInHand", currentCards.size());

            // Broadcast public draw result to all players
            messagingTemplate.convertAndSend("/topic/game/updates", publicResponse);
            
            // Also broadcast updated game state
            broadcastGameUpdate(gameService.getGameState());
            
        } catch (IllegalStateException e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "DRAW_ERROR:" + e.getMessage());
        } catch(Exception e) {
            messagingTemplate.convertAndSend("/topic/game/updates", "DRAW_ERROR:An error occurred while drawing cards");
        }
    }

    public void broadcastGameUpdate(Object gameState) {
        messagingTemplate.convertAndSend("/topic/game/updates", gameState);
    }

    public void sendToPlayer(String playerId, Object message) {
        messagingTemplate.convertAndSend("/queue/player/" + playerId, message);
    }
}