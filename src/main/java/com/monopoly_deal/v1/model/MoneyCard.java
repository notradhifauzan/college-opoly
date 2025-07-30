package com.monopoly_deal.v1.model;

public class MoneyCard extends Card {
    

    public MoneyCard(String id, int value) {
        this.id = id;
        this.value = value;
        this.name = value + "M";
        this.cardType = CardType.MONEY;
    }
}
