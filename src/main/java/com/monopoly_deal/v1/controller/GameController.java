package com.monopoly_deal.v1.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> drawCards() {
        gameService.drawCardsForCurrentPlayer();
        return ResponseEntity.ok("Cards drawn successfully.");
    }
}
