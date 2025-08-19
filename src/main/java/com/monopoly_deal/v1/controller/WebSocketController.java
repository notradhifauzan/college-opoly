package com.monopoly_deal.v1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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

    public void broadcastGameUpdate(Object gameState) {
        messagingTemplate.convertAndSend("/topic/game/updates", gameState);
    }

    public void sendToPlayer(String playerId, Object message) {
        messagingTemplate.convertAndSend("/queue/player/" + playerId, message);
    }
}