package com.monopoly_deal.v1.controller;

import com.monopoly_deal.v1.model.Player;
import com.monopoly_deal.v1.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{playerName}")
    public ResponseEntity<?> getPlayerDetails(@PathVariable String playerName) {
        if (gameService.getGameState() == null) {
            return ResponseEntity.status(404).body("Game not started yet");
        }
        try {
            Player player = gameService.getGameState().getPlayerByName(playerName);
            return ResponseEntity.ok(player);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Player not found: " + playerName);
        }
    }
}
