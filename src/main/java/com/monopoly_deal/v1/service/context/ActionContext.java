package com.monopoly_deal.v1.service.context;

import com.monopoly_deal.v1.dto.PlayCardRequest;
import com.monopoly_deal.v1.engine.GameState;
import com.monopoly_deal.v1.model.Card;
import com.monopoly_deal.v1.model.Player;

import java.util.List;

public class ActionContext {
    private final GameState gameState;
    private final Player player;
    private final Card card;
    private final PlayCardRequest request;

    // generic property
    private final List<String> targetPlayerIds;

    // applicable for sly deal only
    private String targetCardId;

    public ActionContext(GameState gameState, Player player, Card card, PlayCardRequest request) {
        this.gameState = gameState;
        this.player = player;
        this.card = card;
        this.targetPlayerIds = request.getTargetPlayerIds();
        this.request = request;
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

     public String getTargetCardId() {
        return targetCardId;
    }

    public void setTargetCardId(String targetCardId) {
        this.targetCardId = targetCardId;
    }

    public PlayCardRequest getRequest() {
        return request;
    }
}
