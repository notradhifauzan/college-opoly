package com.monopoly_deal.v1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monopoly_deal.v1.dto.PlayCardRequest;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Deck;
import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.GameService;
import com.monopoly_deal.v1.utils.CardLoader;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/status")
    public String getStatus() {
        return "Game server is running!";
    }

    @PostMapping("/start")
    public ResponseEntity<String> startGame(@RequestBody List<String> playerNames) {
        List<Player> players = playerNames.stream().map(Player::new).collect(Collectors.toList());
        Deck deck = new Deck(CardLoader.loadAllCards());
        gameService.startGame(players, deck);
        return ResponseEntity.ok("Game started successfully");
    }

    @GetMapping("/state")
    public ResponseEntity<?> getGameState() {
        if(gameService.getGameState() == null) {
            return ResponseEntity.status(404).body("Game not started yet");
        }
        return ResponseEntity.ok(gameService.getGameState());
    }

    @PostMapping("/draw")
    public ResponseEntity<?> drawCards() {
        try {
            if(gameService.getGameState() == null) {
                return ResponseEntity.status(404).body("Game not started yet");
            }
            
            Player currentPlayer = gameService.getGameState().getCurrentPlayer();
            // get existing player hand
            List<Card> previousCards = new ArrayList<>(currentPlayer.getHand());
            gameService.drawCardsForCurrentPlayer();
            List<Card> currentCards = currentPlayer.getHand();
    
            List<Card> drawnCards = new ArrayList<>(currentCards);
            previousCards.forEach(drawnCards::remove);

            // Create response with player name and drawn cards
            Map<String, Object> response = new HashMap<>();
            response.put("playerName", currentPlayer.getName());
            response.put("playerId", currentPlayer.getId());
            response.put("drawnCards", drawnCards);
            response.put("totalCardsInHand", currentCards.size());
            
            return ResponseEntity.ok().body(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return ResponseEntity.status(500).body("An error occured while drawing cards");
        }

    }

    @PostMapping("/play-card")
    public ResponseEntity<?> playCard(@RequestBody PlayCardRequest request) {
        try {
            if (gameService.getGameState() == null) {
                return ResponseEntity.status(404).body("Game not started yet");
            }

            Player currentPlayer = gameService.getCurrentPlayer();
            gameService.playCardById(currentPlayer.getName(), request);
            
            // Return updated player state instead of simple success message
            Player updatedPlayer = gameService.getCurrentPlayer();
            return ResponseEntity.ok(updatedPlayer);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while playing the card");
        }
    }

    @PostMapping("/end-turn")
    public ResponseEntity<?> endTurn() {
        try {
            if(gameService.getGameState() == null) {
                return ResponseEntity.status(404).body("Game not started yet");
            }
            Player previousPlayer = gameService.getGameState().getCurrentPlayer();
            gameService.endTurn();
            Player nextPlayer = gameService.getGameState().getCurrentPlayer();
            return ResponseEntity.ok("Current player: " + nextPlayer.getName() + "\n Previous player: " + previousPlayer.getName());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occured while ending turn");
        }
    }

    @GetMapping("/random-card")
    public ResponseEntity<?> getRandomCard() {
        try {
            if (gameService.getGameState() == null) {
                return ResponseEntity.status(404).body("Game not started yet");
            }

            Player currentPlayer = gameService.getCurrentPlayer();
            if (currentPlayer.getHand().isEmpty()) {
                return ResponseEntity.status(404).body("Current player has no cards in hand");
            }

            Random random = new Random();
            int randomIndex = random.nextInt(currentPlayer.getHand().size());
            
            PlayCardRequest request = new PlayCardRequest();
            request.setCardId(currentPlayer.getHand().get(randomIndex).getId());
            request.setPlayAsMoney(false);
            
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while getting random card");
        }
    }
}
