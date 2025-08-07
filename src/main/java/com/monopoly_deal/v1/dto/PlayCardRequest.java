package com.monopoly_deal.v1.dto;

public class PlayCardRequest {
    private String cardId;
    private boolean playAsMoney;

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
}