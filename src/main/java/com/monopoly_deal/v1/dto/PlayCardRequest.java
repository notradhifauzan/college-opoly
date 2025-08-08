package com.monopoly_deal.v1.dto;

import java.util.List;

public class PlayCardRequest {
    private String cardId;
    private boolean playAsMoney;
    private List<String> targetPlayerIds;

    // Getters and setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public boolean isPlayAsMoney() {
        return playAsMoney;
    }

    public void setPlayAsMoney(boolean playAsMoney) {
        this.playAsMoney = playAsMoney;
    }

    public List<String> getTargetPlayerIds() {
        return targetPlayerIds;
    }

    public void setTargetPlayerIds(List<String> targetPlayerIds) {
        this.targetPlayerIds = targetPlayerIds;
    }
}