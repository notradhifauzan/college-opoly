package com.monopoly_deal.v1.service.context;

import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;

import java.util.List;

public class ActionContext {
    private final GameState gameState;
    private final Player player;
    private final Card card;
    private final List<String> targetPlayerIds;

    public ActionContext(GameState gameState, Player player, Card card, List<String> targetPlayerIds) {
        this.gameState = gameState;
        this.player = player;
        this.card = card;
        this.targetPlayerIds = targetPlayerIds;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public List<String> getTargetPlayerIds() {
        return targetPlayerIds;
    }
}
