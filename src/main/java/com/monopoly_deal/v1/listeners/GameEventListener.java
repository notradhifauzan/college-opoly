package com.monopoly_deal.v1.listeners;

import com.monopoly_deal.v1.controller.WebSocketController;
import com.monopoly_deal.v1.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    @Autowired
    private WebSocketController webSocketController;

    @EventListener
    public void handleGameStarted(GameStartedEvent event) {
        webSocketController.broadcastGameUpdate(event.getMessage());
    }

    @EventListener
    public void handleCardPlayed(CardPlayedEvent event) {
        webSocketController.broadcastGameUpdate(event.getMessage());
    }

    @EventListener
    public void handleTurnEnded(TurnEndedEvent event) {
        webSocketController.broadcastGameUpdate(event.getMessage());
    }

    @EventListener
    public void handleGameWon(GameWonEvent event) {
        webSocketController.broadcastGameUpdate(event.getMessage());
    }
}